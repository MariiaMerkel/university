package ru.msu.university.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.msu.university.service.SumService;
import ru.msu.university.service.impl.SumServiceImpl;

@RestController
@RequestMapping("/sum")
public class SumController {

    private final SumService sumService;

    public SumController(SumServiceImpl sumService) {
        this.sumService = sumService;
    }

    @GetMapping()
    public Long computeSum() {
        return sumService.computeSum();
    }
}
