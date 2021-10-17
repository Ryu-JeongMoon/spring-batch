package com.example.springbatch.io.spring.step;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("tasklet2 executed");

        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();
        log.info("stepExecutionContext.stepName = {}", stepExecutionContext.get("stepName"));

        String stepName = chunkContext.getStepContext().getStepName();

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }

        log.info("stepName = {}", stepName);

        return RepeatStatus.FINISHED;
    }
}
