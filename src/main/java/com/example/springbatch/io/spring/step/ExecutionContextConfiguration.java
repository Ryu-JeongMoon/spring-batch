package com.example.springbatch.io.spring.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContextTasklet1 tasklet1;
    private final ExecutionContextTasklet2 tasklet2;
    private final ExecutionContextTasklet3 tasklet3;
    private final ExecutionContextTasklet4 tasklet4;

    @Bean
    public Job executionJobs() {
        return jobBuilderFactory.get("executionJobs")
            .start(executionsStep1())
            .next(executionsStep2())
            .next(executionsStep3())
            .next(executionsStep4())
            .build();
    }

    @Bean
    public Step executionsStep1() {
        return stepBuilderFactory.get("executionsStep1")
            .tasklet(tasklet1)
            .build();
    }

    @Bean
    public Step executionsStep2() {
        return stepBuilderFactory.get("executionsStep2")
            .tasklet(tasklet2)
            .build();
    }

    @Bean
    public Step executionsStep3() {
        return stepBuilderFactory.get("executionsStep3")
            .tasklet(tasklet3)
            .build();
    }

    @Bean
    public Step executionsStep4() {
        return stepBuilderFactory.get("executionsStep4")
            .tasklet(tasklet4)
            .build();
    }
}
