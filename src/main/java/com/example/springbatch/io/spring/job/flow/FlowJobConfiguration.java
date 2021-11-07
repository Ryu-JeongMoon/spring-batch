package com.example.springbatch.io.spring.job.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
public class FlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
            .incrementer(new RunIdIncrementer())
            .start(flowStep1())

            .on("COMPLETED")
            .to(flowStep3())

            .from(flowStep1())
            .on("FAILED")
            .to(flowStep2())
            .end()
            .build();
    }

    @Bean
    public Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
            .tasklet((contribution, chunkContext) -> {
                throw new RuntimeException("FAILED");
//                System.out.println("flowStep1 executed");
//                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("flowStep2 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step flowStep3() {
        return stepBuilderFactory.get("flowStep3")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("flowStep3 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
