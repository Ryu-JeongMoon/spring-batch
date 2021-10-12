package com.example.springbatch.io.job;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class JobExecutionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job executionJob() {
        return jobBuilderFactory.get("executionJob")
            .start(executionStep1())
            .next(executionStep2())
            .next(executionStep3())
            .build();
    }

    @Bean
    public Step executionStep1() {
        return stepBuilderFactory.get("executionStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("executionStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step executionStep2() {
        return stepBuilderFactory.get("executionStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("executionStep2 executed");
//                throw new RuntimeException("step 2 failed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step executionStep3() {
        return stepBuilderFactory.get("executionStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info("executionStep3 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
