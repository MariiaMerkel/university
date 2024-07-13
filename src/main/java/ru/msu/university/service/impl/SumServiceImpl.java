package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.service.SumService;

import java.util.stream.Stream;

@Service
public class SumServiceImpl implements SumService {
    @Override
    public Long computeSum() {
        return Long.valueOf(Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum));
    }
}
