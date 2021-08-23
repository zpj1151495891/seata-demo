package com.example.testbusinessservice.config;

import lombok.extern.slf4j.Slf4j;
import net.trueland.tcloud.scrm.common.model.Rsp;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
@ResponseBody
@Slf4j
public class ControllerAdvice{

    @ExceptionHandler(Exception.class)
    public Rsp<?> handleNullPointerException(Exception e) {
        return Rsp.fail(e.getMessage());
    }
}
