package com.example.springbatch.io.spring.job.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class TaskletConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletJob() {
        return jobBuilderFactory.get("taskletJob")
            .incrementer(new RunIdIncrementer())
            .start(taskletStep1())
            .next(taskletStep2())
            .build();
    }

    @Bean
    public Step taskletStep1() {
        return stepBuilderFactory.get("taskletStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info("taskletStep1 executed");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step taskletStep2() {
        return stepBuilderFactory.get("taskletStep2")
            .tasklet(new CustomTasklet())
            .build();
    }
}
