package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.service.SumService;

import java.util.stream.Stream;

@Service
public class SumServiceImpl implements SumService {
    @Override
    public Long computeSum() {
        return Stream.iterate(1L, a -> a + 1)
                .limit(1000000)
                .parallel()
                .reduce(0L, Long::sum);
    }
}
