package com.example.bookingapp.service;

import com.example.bookingapp.entity.User;
import com.example.bookingapp.entity.UserRole;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.repository.UserRepository;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет пользователя с ID " + id));
    }

    public User findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет пользователя с логином " + name));
    }

    @Transactional
    public User create(User user, String role) {
        checkNameAndEmailForCreate(user);

        user.setRole(userRoleFromRole(role));
        return repository.save(user);
    }

    @Transactional
    public User update(User user) {
        User existedUser = findById(user.getId());

        checkNameAndEmailForUpdate(user, existedUser);

        BeanUtils.nonNullPropertiesCopy(user, existedUser);

        return repository.save(existedUser);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }



    public UserRole userRoleFromRole(String role) {
        if (role == null) {
            throw new IncorrectRequestException("В запросе необходимо указать параметр role");
        }
        if (role.matches("USER|ADMIN")) {
            return UserRole.valueOf(role);
        }
        throw new IncorrectRequestException("У параметра role должно быть значение 'USER' или 'ADMIN'");
    }

    public void checkNameAndEmailForCreate(User user) {
        checkName(user.getName());
        checkEmail(user.getEmail());
    }

    public void checkNameAndEmailForUpdate(User user, User existedUser) {
        if ( !user.getName().equals(existedUser.getName()) ) {
            checkName(user.getName());
        }
        if ( !user.getEmail().equals(existedUser.getEmail()) ) {
            checkEmail(user.getEmail());
        }
    }

    public void checkName(String name) {
        if (repository.findByName(name).isPresent()) {
            throw new IncorrectRequestException("Пользователь с таким именем уже есть в базе данных");
        }
    }

    public void checkEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IncorrectRequestException("Пользователь с таким email уже есть в базе данных");
        }
    }

    // for BookingMapper
    public Long idByUser(User user) {
        return user.getId();
    }
}