package com.example.springbatch.io.spring.job.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class AllowStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job allowStepJob() {
        return jobBuilderFactory.get("allowStepJob")
            .start(allowStepStep1())
            .next(allowStepStep2())
            .build();
    }

    @Bean
    public Step allowStepStep1() {
        return stepBuilderFactory.get("allowStepStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info("allowStepStep1 executed");
                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public Step allowStepStep2() {
        return stepBuilderFactory.get("allowStepStep2")
            .tasklet(((contribution, chunkContext) -> {
                log.info("allowStepStep1 executed");
                throw new RuntimeException("PANDA");
//                return RepeatStatus.FINISHED;
            }))
            .startLimit(3)
            .build();
    }
}
