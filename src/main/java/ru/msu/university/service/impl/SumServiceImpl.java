package ru.msu.university.service.impl;

import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import ru.msu.university.service.SumService;

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
