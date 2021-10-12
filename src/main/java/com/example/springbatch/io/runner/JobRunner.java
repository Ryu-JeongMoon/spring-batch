package com.example.springbatch.io.runner;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job parameterJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user2")
            .addLong("seq", 3L)
            .addDouble("age", 27.7)
            .addDate("requestDate", new Date())
            .toJobParameters();

        jobLauncher.run(parameterJob, jobParameters);
    }
}
