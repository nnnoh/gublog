package com.mine.gublog.exception.handler;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.utils.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ResultWrapper handleMethodArgumentNotValidException(HttpServletRequest request, BindException e) {
        return ResultWrapper.fail(ResultConstants.PARAM_INVALID, e.getMessage());
    }
}
