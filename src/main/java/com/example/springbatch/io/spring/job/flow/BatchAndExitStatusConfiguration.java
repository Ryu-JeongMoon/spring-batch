package com.example.springbatch.io.spring.job.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
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
public class BatchAndExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job statusJob() {
        return jobBuilderFactory.get("statusJob")
            .incrementer(new RunIdIncrementer())
            .start(statusStep1())
            .next(statusStep2())
            .next(statusStep3())
            .build();
    }

    @Bean
    public Step statusStep1() {
        return stepBuilderFactory.get("statusStep1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("statusStep1 executed");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step statusStep2() {
        return stepBuilderFactory.get("statusStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("statusStep2 executed");
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step statusStep3() {
        return stepBuilderFactory.get("statusStep3")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("statusStep3 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
