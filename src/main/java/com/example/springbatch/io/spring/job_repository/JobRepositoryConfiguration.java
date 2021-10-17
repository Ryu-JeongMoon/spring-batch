package com.example.springbatch.io.spring.job_repository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobRepositoryListener;

    @Bean
    public Job repositoryJob() {
        return jobBuilderFactory.get("repositoryJob")
            .start(repositoryStep1())
            .next(repositoryStep2())
            .next(repositoryStep3())
            .build();
    }

    @Bean
    public Step repositoryStep1() {
        return stepBuilderFactory.get("repositoryStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    System.out.println("================================");
                    System.out.println("======Hello Spring Batch!======");
                    System.out.println("================================");

                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step repositoryStep2() {
        return stepBuilderFactory.get("repositoryStep2")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    System.out.println("================================");
                    System.out.println("======Hello Spring Batch!!======");
                    System.out.println("================================");

                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step repositoryStep3() {
        return stepBuilderFactory.get("repositoryStep3")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("================================");
                System.out.println("======Hello Spring Batch!!!======");
                System.out.println("================================");

                return RepeatStatus.FINISHED;
            }).build();
    }
}
