package com.example.springbatch.io.spring.job.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class JobStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob")
            .incrementer(new RunIdIncrementer())
            .start(jobStep(null))
            .next(parentStep())
            .build();
    }

    @Bean
    public Step jobStep(JobLauncher jobLauncher) {
        return stepBuilderFactory.get("jobStep")
            .job(childJob())
            .launcher(jobLauncher)
            .parametersExtractor(jobParametersExtractor())
            .listener(new StepExecutionListener() {
                @Override
                public void beforeStep(StepExecution stepExecution) {
                    stepExecution.getExecutionContext().putString("name", "user");
                }

                @Override
                public ExitStatus afterStep(StepExecution stepExecution) {
                    log.info("afterStep executed");
                    return null;
                }
            })
            .build();
    }

    @Bean
    public Job childJob() {
        return jobBuilderFactory.get("childJob")
            .start(childStep())
            .build();
    }

    private DefaultJobParametersExtractor jobParametersExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }

    @Bean
    public Step childStep() {
        return stepBuilderFactory.get("childStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("childStep executed");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step parentStep() {
        return stepBuilderFactory.get("parentStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("parentStep executed");
//                throw new RuntimeException("Hello");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
