package com.example.springbatch.io.spring.job.simplejob;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simplestJob() {
        return jobBuilderFactory.get("simplestJob")
            .start(simplestStep1())
            .next(simplestStep2())
            .next(simplestStep3())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Step simplestStep1() {
        return stepBuilderFactory.get("simplestStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("simplestStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step simplestStep2() {
        return stepBuilderFactory.get("simplestStep2")
            .tasklet((contribution, chunkContext) -> {
                chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                log.info("simplestStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step simplestStep3() {
        return stepBuilderFactory.get("simplestStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info("simplestStep3 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }

}
