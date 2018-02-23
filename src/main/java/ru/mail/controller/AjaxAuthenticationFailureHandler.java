package ru.mail.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.mail.service.util.SendUserJSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Denis Monich
 * this class send json object to UI if was happened failure user authentication
 */
@Component("ajaxAuthenticationFailureHandler")
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger logger = Logger.getLogger(AjaxAuthenticationFailureHandler.class);

    private final Properties properties;

    @Autowired
    public AjaxAuthenticationFailureHandler(Properties properties) {
        this.properties = properties;
    }

    /**
     * @param request default parameter for override method "onAuthenticationFailure"
     * @param response for creating and sending json object
     * @param exception default parameter for override method "onAuthenticationFailure"
     * @throws IOException can be throw during reading the {@link Properties} file
     * @throws ServletException to indicate that servlet is permanently or temporarily unavailable.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.error("Sending JSON: failure authentication");
        /*
          public static method for creating and sending json object
         */
        SendUserJSON.sendJSON(
                response,
                properties.getProperty("invalide.msg"),
                properties.getProperty("invalide.code"),
                properties.getProperty("content.type"),
                properties.getProperty("encoding.type")
        );
    }
}
