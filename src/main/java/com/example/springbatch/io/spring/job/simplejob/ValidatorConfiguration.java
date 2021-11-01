package com.example.springbatch.io.spring.job.simplejob;

import com.example.springbatch.io.spring.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
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
public class ValidatorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job validatorJob() {
        return jobBuilderFactory.get("validatorJob")
            .start(validatorStep1())
            .next(validatorStep2())
//            .validator(new CustomValidator())
            .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
            .build();
    }

    @Bean
    public Step validatorStep1() {
        return stepBuilderFactory.get("validatorStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    log.info("validatorStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step validatorStep2() {
        return stepBuilderFactory.get("validatorStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("validatorStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
