package ru.msu.university.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.msu.university.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }
}
