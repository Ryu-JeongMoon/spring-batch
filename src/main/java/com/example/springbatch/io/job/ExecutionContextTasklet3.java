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
public class ExecutionContextTasklet3 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("tasklet3 executed");

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
            .getExecutionContext();

        if (jobExecutionContext.get("name") == null) {
            jobExecutionContext.put("name", "user");
//            throw new RuntimeException("tasklet3 was failed");
        }

        return RepeatStatus.FINISHED;
    }
}
