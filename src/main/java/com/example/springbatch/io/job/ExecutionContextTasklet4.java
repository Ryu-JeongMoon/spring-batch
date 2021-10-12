package com.example.springbatch.io.job;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ExecutionContextTasklet4 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("tasklet1 executed");

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
            .getExecutionContext();

        log.info("name = {}", jobExecutionContext.get("name"));

        return RepeatStatus.FINISHED;
    }
}
