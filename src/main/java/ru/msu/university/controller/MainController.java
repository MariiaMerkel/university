package ru.msu.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class MainController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("sum")
    public int getNum() {
        return IntStream.range(1, 1_000_000).reduce(0, Integer::sum);
    }
}
