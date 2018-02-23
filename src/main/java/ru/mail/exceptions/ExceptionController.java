package ru.mail.exceptions;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Denis Monich
 * this class is view jsp error page if throw any exception during application running
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = Logger.getLogger(ExceptionController.class);

    /**
     *
     * @param req request from UI
     * @param e Class's Exception exception
     * @return jsp name
     */
    @ExceptionHandler(value = Exception.class)
    public String handleError(HttpServletRequest req, Exception e){
        logger.error("Request "+req.getRequestURL()+" raised "+e);
        return "error";
    }
}
