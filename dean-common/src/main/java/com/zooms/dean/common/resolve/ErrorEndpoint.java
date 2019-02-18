package com.zooms.dean.common.resolve;

import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author slacrey
 * @since 2018/1/17
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorEndpoint implements ErrorController {

    @Value("${error.path:/error}")
    private String path = "/error";

    @RequestMapping
    @ResponseBody
    public View<Void> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = String.valueOf(request.getAttribute("javax.servlet.error.message"));
        if (statusCode == 404) {
            return View.of404Error(message);
        } else if (statusCode == 500) {
            return View.of500Error(message);
        }
        return View.ofError(message);
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}
