package ru.msu.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

import static java.time.LocalTime.now;

@RestController
public class MainController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("sum")
    public int getNum() {
        logger.debug();
        int sum = Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b );
        return sum;
    }
}
