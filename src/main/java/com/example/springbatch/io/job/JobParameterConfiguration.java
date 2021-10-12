package com.example.springbatch.io.job;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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
public class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parameterJob() {
        return jobBuilderFactory.get("parameterJob")
            .start(parameterStep1())
            .next(parameterStep2())
            .next(parameterStep3())
            .build();
    }

    @Bean
    public Step parameterStep1() {
        return stepBuilderFactory.get("parameterStep1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();

                    String name = jobParameters.getString("name");
                    Long seq = jobParameters.getLong("seq");
                    Double age = jobParameters.getDouble("age");
                    Date requestDate = jobParameters.getDate("requestDate");
                    log.info("name = {}, seq = {}, age = {}, requestDate = {}", name, seq, age, requestDate);
                    log.info("parameterStep1 executed");
                    return RepeatStatus.FINISHED;
                }
            }).build();
    }

    @Bean
    public Step parameterStep2() {
        return stepBuilderFactory.get("parameterStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info("parameterStep2 executed");
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step parameterStep3() {
        return stepBuilderFactory.get("parameterStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info("parameterStep3 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
