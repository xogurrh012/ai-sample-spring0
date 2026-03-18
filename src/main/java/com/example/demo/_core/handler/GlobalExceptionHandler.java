package com.example.demo._core.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo._core.handler.exception.Exception400;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception400.class)
    public String handleException400(Exception400 e) {
        String script = """
                <script>
                    alert('%s');
                    history.back();
                </script>
                """;
        return String.format(script, e.getMessage());
    }

}
