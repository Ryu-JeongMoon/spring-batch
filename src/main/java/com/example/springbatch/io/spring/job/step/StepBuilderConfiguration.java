package com.example.springbatch.io.spring.job.step;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class StepBuilderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepBuilderJob() {
        return jobBuilderFactory.get("stepBuilderJob")
            .incrementer(new RunIdIncrementer())
            .start(stepBuilderStep1())
            .next(stepBuilderStep2())
            .next(stepBuilderStep3())
            .next(stepBuilderStep4())
            .next(stepBuilderStep5())
            .build();
    }

    @Bean
    public Step stepBuilderStep1() {
        return stepBuilderFactory.get("stepBuilderStep1")
            .<String, String>chunk(3)
            .reader(new ItemReader<String>() {
                @Override
                public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    return null;
                }
            })
            .processor(new ItemProcessor<String, String>() {
                @Override
                public String process(String item) throws Exception {
                    return null;
                }
            })
            .writer(new ItemWriter<String>() {
                @Override
                public void write(List<? extends String> items) throws Exception {

                }
            })
            .build();
    }

    @Bean
    public Step stepBuilderStep2() {
        return stepBuilderFactory.get("stepBuilderStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info("stepBuilderStep2 executed");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step stepBuilderStep3() {
        return stepBuilderFactory.get("stepBuilderStep3")
            .partitioner(stepBuilderStep1())
            .gridSize(2)
            .build();
    }

    @Bean
    public Step stepBuilderStep4() {
        return stepBuilderFactory.get("stepBuilderStep4")
            .job(stepBuilderJob2())
            .build();
    }

    @Bean
    public Step stepBuilderStep5() {
        return stepBuilderFactory.get("stepBuilderStep5")
            .flow(stepBuilderFlow())
            .build();
    }

    @Bean
    public Job stepBuilderJob2() {
        return this.jobBuilderFactory.get("stepBuilderJob2")
            .start(stepBuilderStep1())
            .next(stepBuilderStep2())
            .next(stepBuilderStep3())
            .build();
    }

    @Bean
    public Flow stepBuilderFlow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("stepBuilderFlow");
        flowBuilder.start(stepBuilderStep2()).end();
        return flowBuilder.build();
    }
}
