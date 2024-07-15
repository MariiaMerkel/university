package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.msu.university.service.InfoService;
import ru.msu.university.service.impl.InfoServiceImpl;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }

    @GetMapping("port")
    public ResponseEntity<Integer> get() {
        return ResponseEntity.ok(infoService.getPort());
    }
}
