package com.example.springbatch.io.spring.job.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class StartNextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job startNextJob() {
        return jobBuilderFactory.get("startNextJob")
            .incrementer(new RunIdIncrementer())
            .start(startNextA())
            .next(startNextStep3())
            .next(startNextB())
            .next(startNextStep6())
            .end()
            .build();
    }

    @Bean
    public Flow startNextA() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("startNextA");
        builder.start(startNextStep1())
            .next(startNextStep2())
            .end();
        return builder.build();
    }

    @Bean
    public Flow startNextB() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("startNextB");
        builder.start(startNextStep4())
            .next(startNextStep5())
            .end();
        return builder.build();
    }

    @Bean
    public Step startNextStep1() {
        return stepBuilderFactory.get("startNextStep1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("startNextStep1 executed");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step startNextStep2() {
        return stepBuilderFactory.get("startNextStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("startNextStep2 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step startNextStep3() {
        return stepBuilderFactory.get("startNextStep3")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("startNextStep3 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step startNextStep4() {
        return stepBuilderFactory.get("startNextStep4")
            .tasklet(((contribution, chunkContext) -> {
                throw new RuntimeException("FAILED");
//                System.out.println("startNextStep4 executed");
//                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step startNextStep5() {
        return stepBuilderFactory.get("startNextStep5")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("startNextStep5 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step startNextStep6() {
        return stepBuilderFactory.get("startNextStep6")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("startNextStep6 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
