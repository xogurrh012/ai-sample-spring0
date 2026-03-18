package com.example.demo._core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.example.demo._core.handler.exception.Exception400;

@Component
@Aspect
public class ValidAspect {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validate(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors errors) {
                if (errors.hasErrors()) {
                    throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
                }
            }
        }
    }
}
