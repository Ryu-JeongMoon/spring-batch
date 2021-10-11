package com.example.springbatch.jojoldu.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SimpleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;      // Job  생성
    private final StepBuilderFactory stepBuilderFactory;    // Step 생성

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
            .start(simpleStep1(null))
            .next(simpleStep2(null))
            .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep")
            .tasklet(((contribution, chunkContext) -> {
//                throw new IllegalArgumentException("step1에서 즉시 실패");
                log.info(">>>>> This is Step - 2");
                log.info(">>>>> requestDate = {}", requestDate);
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep")
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is Step - 2");
                log.info(">>>>> requestDate = {}", requestDate);
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
