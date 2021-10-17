application.yml 에 아래와 같은 설정을 했을 때 실행할 Job 여러개 줄 수 있으나
~~~
spring:
    batch:
        job:
          names: ${job.name:NONE}
~~~

JobLauncherApplicationRunner Class 의 설정에 따라 Job 이름을 나누는데 split(",") 로 구분하기 때문에 띄어쓰기 하면 인식 못 함

~~~
private void executeLocalJobs(JobParameters jobParameters) throws JobExecutionException {
    for (Job job : this.jobs) {
        if (StringUtils.hasText(this.jobNames)) {
            String[] jobsToRun = this.jobNames.split(",");
            if (!PatternMatchUtils.simpleMatch(jobsToRun, job.getName())) {
                logger.debug(LogMessage.format("Skipped job: %s", job.getName()));
                continue;
            }
        }
        execute(job, jobParameters);
    }
}

~~~