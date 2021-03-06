package com.example.springbatch.io.spring.step;

import com.example.springbatch.io.spring.CustomTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
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
public class StepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
            .start(batchStep1())
            .next(batchStep2())
            .build();
    }

    @Bean
    public Step batchStep1() {
        return stepBuilderFactory.get("batchStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("================================");
                    log.info("======Hello Spring Batch!======");
                    log.info("================================");

                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step batchStep2() {
        return stepBuilderFactory.get("batchStep2")
            .tasklet(new CustomTasklet()).build();
    }
}
