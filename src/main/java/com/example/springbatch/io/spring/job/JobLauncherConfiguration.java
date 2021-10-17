package com.example.springbatch.io.spring.job;

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
public class JobLauncherConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job launcherJob() {
        return jobBuilderFactory.get("launcherJob")
            .start(launcherStep1())
            .next(launcherStep2())
            .build();
    }

    @Bean
    public Step launcherStep1() {
        return stepBuilderFactory.get("launcherStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("launcherStep1 executed");
                    Thread.sleep(5000);
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step launcherStep2() {
        return stepBuilderFactory.get("launcherStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("launcherStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
