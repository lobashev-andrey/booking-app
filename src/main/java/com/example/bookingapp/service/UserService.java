package com.example.bookingapp.service;

import com.example.bookingapp.entity.User;
import com.example.bookingapp.entity.UserRole;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.repository.UserRepository;
import com.example.bookingapp.dto.KafkaMessage;
import com.example.bookingapp.dto.KafkaUserMessage;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${app.kafka.kafka-user-topic}")
    private String userTopic;

    private final UserRepository repository;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public List<User> findAll() {
        log.debug("findAll() method is called");


        return repository.findAll();
    }

    public User findById(Long id) {
        log.debug("findById() method is called with id={}", id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет пользователя с ID " + id));
    }

    public User findByName(String name) {
        log.debug("findByName() method is called with name={}", name);

        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет пользователя с логином " + name));
    }

    @Transactional
    public User create(User user, String role) {
        log.debug("create() method is called with role={}", role);

        checkNameAndEmailForCreate(user);

        user.setRole(userRoleFromRole(role));

        User createdUser = repository.save(user);
        kafkaTemplate.send(userTopic, new KafkaUserMessage(createdUser.getId()));

        return createdUser;
    }

    @Transactional
    public User update(User user) {
        log.debug("update() method is called");

        User existedUser = findById(user.getId());

        checkNameAndEmailForUpdate(user, existedUser);

        BeanUtils.nonNullPropertiesCopy(user, existedUser);

        return repository.save(existedUser);
    }

    public void delete(Long id) {
        log.debug("delete() method is called with id={}", id);

        repository.delete(findById(id));
    }



    public UserRole userRoleFromRole(String role) {
        log.debug("userRoleFromRole() method is called with role={}", role);

        if (role == null) {
            throw new IncorrectRequestException("В запросе необходимо указать параметр role");
        }
        try {
            return UserRole.valueOf(role);
        } catch (IllegalArgumentException ex){
            throw new IncorrectRequestException("У параметра role должно быть значение 'USER' или 'ADMIN'");
        }
    }

    public void checkNameAndEmailForCreate(User user) {
        log.debug("checkNameAndEmailForCreate() method is called");

        checkName(user.getName());
        checkEmail(user.getEmail());
    }

    public void checkNameAndEmailForUpdate(User user, User existedUser) {
        log.debug("checkNameAndEmailForUpdate() method is called");

        if ( !user.getName().equals(existedUser.getName()) ) {
            checkName(user.getName());
        }
        if ( !user.getEmail().equals(existedUser.getEmail()) ) {
            checkEmail(user.getEmail());
        }
    }

    public void checkName(String name) {
        log.debug("checkName() method is called");

        if (repository.findByName(name).isPresent()) {
            throw new IncorrectRequestException("Пользователь с таким именем уже есть в базе данных");
        }
    }

    public void checkEmail(String email) {
        log.debug("checkEmail() method is called");

        if (repository.findByEmail(email).isPresent()) {
            throw new IncorrectRequestException("Пользователь с таким email уже есть в базе данных");
        }
    }

    // for BookingMapper
    public Long idByUser(User user) {
        log.debug("idByUser() method is called");

        return user.getId();
    }
}
