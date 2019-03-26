package com.shtrade.userservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler
{
    private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = DataIllegalException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> handleResourceNotFoundException(DataIllegalException e)
    {
        Map<String,Object> result = new HashMap<>();
        result.put("code", e.getCode());
        result.put("message", e.getMessage());
        logger.error(e.getMessage(), e);
        return result;
    }
}
