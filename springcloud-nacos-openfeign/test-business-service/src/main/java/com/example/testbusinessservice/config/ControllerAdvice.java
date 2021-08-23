package com.example.testbusinessservice.config;

import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.nacos.common.model.RestResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
@ResponseBody
@Slf4j
public class ControllerAdvice{

    @ExceptionHandler(Exception.class)
    public RestResult<?> handleNullPointerException(Exception e) {
        return RestResultUtils.failed(e.getMessage());
    }
}
