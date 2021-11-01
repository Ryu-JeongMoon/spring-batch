package com.example.springbatch.io.spring.job.simplejob;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
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
public class SimpleJobArchitectureConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job architectureJob() {
        return jobBuilderFactory.get("architectureJob")
            .start(architectureStep1())
            .next(architectureStep2())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Step architectureStep1() {
        return stepBuilderFactory.get("architectureStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("architectureStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step architectureStep2() {
        return stepBuilderFactory.get("architectureStep2")
            .tasklet((contribution, chunkContext) -> {
                chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                log.info("architectureStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
