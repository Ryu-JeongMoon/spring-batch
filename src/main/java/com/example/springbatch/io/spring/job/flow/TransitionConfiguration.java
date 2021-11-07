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
public class TransitionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job transitionJob() {
        return jobBuilderFactory.get("transitionJob")
            .incrementer(new RunIdIncrementer())
            .start(transitionStep1())
            .on("FAILED")
            .to(transitionStep2())
            .on("FAILED")
            .stop()

            .from(transitionStep1())
            .on("*")
            .to(transitionStep3())
            .next(transitionStep4())
            .on("*")
            .end()

            .from(transitionStep2())
            .on("*")
            .to(transitionStep5())

            .end()
            .build();
    }

    @Bean
    public Step transitionStep1() {
        return stepBuilderFactory.get("transitionStep1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("transitionStep1 executed");
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step transitionStep2() {
        return stepBuilderFactory.get("transitionStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("transitionStep2 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step transitionStep3() {
        return stepBuilderFactory.get("transitionStep3")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("transitionStep3 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step transitionStep4() {
        return stepBuilderFactory.get("transitionStep4")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("transitionStep4 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step transitionStep5() {
        return stepBuilderFactory.get("transitionStep5")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("transitionStep5 executed");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
