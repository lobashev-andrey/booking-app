package com.example.bookingapp.statistics.data;

import com.example.bookingapp.statistics.data.entity.BookingData;
import com.example.bookingapp.statistics.data.entity.UserData;
import lombok.RequiredArgsConstructor;
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
public class StatController {

    private final UserStatService userStatService;

    private final BookingStatService bookingStatService;

    @GetMapping("/user")
    public Flux<UserData> getUserStat() {

        return userStatService.findAll();
    }

    @GetMapping("/booking")
    public Flux<BookingData> getBookingStat(){

        return bookingStatService.findAll();
    }

    @PostMapping("/file/upload")
    public void uploadStat() throws IOException {
        Path path = Paths.get("build/resources/main/statistics.csv");

        Files.write(path, userStatService.findAll().map(UserData::toString).toIterable());
        Files.write(path, bookingStatService.findAll().map(BookingData::toString).toIterable(), StandardOpenOption.APPEND);
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> downloadStatFile(){

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
