package com.example.bookingapp.controller;

import com.example.bookingapp.service.BookingStatService;
import com.example.bookingapp.service.UserStatService;
import com.example.bookingapp.entity.BookingData;
import com.example.bookingapp.entity.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stat")
@Slf4j
public class StatController {

    private final UserStatService userStatService;

    private final BookingStatService bookingStatService;

    @GetMapping("/user")
    public Flux<UserData> getUserStat() {
        log.info("getUserStat() method is called");

        return userStatService.findAll();
    }

    @GetMapping("/booking")
    public Flux<BookingData> getBookingStat(){
        log.info("getBookingStat() method is called");

        return bookingStatService.findAll();
    }

    @PostMapping("/file/upload")
    public void uploadStat() throws IOException {
        log.info("uploadStat() method is called");

        Path path = Paths.get("build/resources/main/statistics.csv");

        Files.write(path, userStatService.findAll().map(UserData::toString).toIterable());
        Files.write(path, bookingStatService.findAll().map(BookingData::toString).toIterable(), StandardOpenOption.APPEND);
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> downloadStatFile(){
        log.info("downloadStatFile() method is called");

        Resource fileResource = new ClassPathResource("statistics.csv");

        if(!fileResource.exists()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv");
        headers.setContentType(MediaType.parseMediaType("text/csv"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
