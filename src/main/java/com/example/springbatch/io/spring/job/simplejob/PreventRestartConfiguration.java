package com.example.springbatch.io.spring.job.simplejob;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class PreventRestartConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job preventJob() {
        return jobBuilderFactory.get("preventJob")
            .start(preventStep1())
            .next(preventStep2())
            .preventRestart()
            .build();
    }

    @Bean
    public Step preventStep1() {
        return stepBuilderFactory.get("preventStep1")
            .tasklet((contribution, chunkContext) -> {
                    log.info("preventStep1 executed");
                    return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step preventStep2() {
        return stepBuilderFactory.get("preventStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("preventStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}

/*
SimpleJobLauncher 에서 조건 검사하여 재실행 못하도록 에러 터트림
JobExecution lastExecution = jobRepository.getLastJobExecution(job.getName(), jobParameters);

어떤 경우에 재실행 해서는 안 되는걸까?!
 */