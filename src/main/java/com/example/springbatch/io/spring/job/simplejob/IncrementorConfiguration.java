package com.example.springbatch.io.spring.job.simplejob;

import com.example.springbatch.io.spring.incrementor.CustomIncrementor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class IncrementorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job incrementorJob() {
        return jobBuilderFactory.get("incrementorJob")
            .start(incrementorStep1())
            .next(incrementorStep2())
            .incrementer(new CustomIncrementor())
            .build();
    }

    @Bean
    public Step incrementorStep1() {
        return stepBuilderFactory.get("incrementorStep1")
            .tasklet((contribution, chunkContext) -> {
                    log.info("incrementorStep1 executed");
                    return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step incrementorStep2() {
        return stepBuilderFactory.get("incrementorStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("incrementorStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}

/*
오홍 incrementor 를 써야 parameter 신경 안 쓰고 계속 실행할 수 있겠네
 */