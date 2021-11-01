package com.example.springbatch.io.spring.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if (parameters.getString("name") == null)
            throw new JobParametersInvalidException("삐빅 필수값 없음");
    }
}

/*
커스텀 설정으로 클래스 새로 만들어 여기서 직접 지정하거나 DefaultJobParametersValidator 클래스를 이용한다
DefaultJobParametersValidator 얘는 String 배열로 인자 두개 받음 requiredKeys & optionalKeys
 */