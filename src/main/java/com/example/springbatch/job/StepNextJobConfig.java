package com.example.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class StepNextJobConfig {

    private final JobBuilderFactory jobBuilderFactory;      // Job  생성
    private final StepBuilderFactory stepBuilderFactory;    // Step 생성

    @Bean
    public Job stepNextJob() {
        return jobBuilderFactory.get("stepNextJob")
            .start(step1())
            .next(step2())
            .next(step3())
            .build();
    }

    @Bean
    @JobScope
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is Step - 1");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    @JobScope
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is Step - 2");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    @JobScope
    public Step step3() {
        return stepBuilderFactory.get("step3")
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is Step - 3");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
