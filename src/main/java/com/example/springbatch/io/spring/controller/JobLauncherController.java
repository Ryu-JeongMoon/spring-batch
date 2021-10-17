package com.example.springbatch.io.spring.controller;

import java.util.Date;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final Job launcherJob;
    private final JobLauncher jobLauncher;
    private final BasicBatchConfigurer basicBatchConfigurer;

    @PostMapping("/sync")
    public String launchSync(@RequestBody Member member) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("id", member.getId())
            .addDate("data", new Date())
            .toJobParameters();

        jobLauncher.run(launcherJob, jobParameters);

        return "Batch Finished Sync";
    }

    // sync
    @PostMapping("/async")
    public String launchAsync(@RequestBody Member member) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("id", member.getId())
            .addDate("data", new Date())
            .toJobParameters();

        SimpleJobLauncher simpleJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        simpleJobLauncher.run(launcherJob, jobParameters);

        return "Batch Finished Async";
    }
}
