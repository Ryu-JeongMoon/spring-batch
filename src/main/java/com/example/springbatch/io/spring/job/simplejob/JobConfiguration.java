package com.example.springbatch.io.spring.job.simplejob;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job configurationJob() {
        return jobBuilderFactory.get("configurationJob")
            .start(configurationStep1())
            .next(configurationStep2())
            .build();
    }

    @Bean
    public Job configurationJob2() {
        return jobBuilderFactory.get("configurationJob2")
            .start(flow())
            .next(configurationStep5())
            .end()
            .build();
    }

    @Bean
    public Step configurationStep1() {
        return stepBuilderFactory.get("configurationStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("configurationStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step configurationStep2() {
        return stepBuilderFactory.get("configurationStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("configurationStep2 executed");
//                throw new RuntimeException("step 2 failed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(configurationStep3())
            .next(configurationStep4())
            .end();
        return flowBuilder.build();
    }

    @Bean
    public Step configurationStep3() {
        return stepBuilderFactory.get("configurationStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info("configurationStep3 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step configurationStep4() {
        return stepBuilderFactory.get("configurationStep4")
            .tasklet((contribution, chunkContext) -> {
                log.info("configurationStep4 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step configurationStep5() {
        return stepBuilderFactory.get("configurationStep5")
            .tasklet((contribution, chunkContext) -> {
                log.info("configurationStep5 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
