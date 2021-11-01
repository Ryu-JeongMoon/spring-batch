package com.example.springbatch.io.spring.incrementor;

import java.util.Date;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class CustomIncrementor implements JobParametersIncrementer {

    private String key = "date";

    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder(parameters == null ? new JobParameters() : parameters)
            .addDate(this.key, new Date())
            .toJobParameters();
    }
}
