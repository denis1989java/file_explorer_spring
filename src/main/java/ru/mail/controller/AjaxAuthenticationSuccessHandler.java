package ru.mail.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.mail.service.util.SendUserJSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Denis Monich
 * this class send json object to UI if was happened success user authentication
 */
@Component("ajaxAuthenticationSuccessHandler")
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(AjaxAuthenticationSuccessHandler.class);
    private final Properties properties;

    @Autowired
    public AjaxAuthenticationSuccessHandler(Properties properties) {
        this.properties = properties;
    }

    /**
     * @param request default parameter for override method "onAuthenticationFailure"
     * @param response for creating and sending json object
     * @param authentication default parameter for override method "onAuthenticationFailure"
     * @throws IOException can be throw during reading the {@link Properties} file
     * @throws ServletException to indicate that servlet is permanently or temporarily unavailable.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.error("Sending JSON: failure authentication");
        /*
          public static method for creating and sending json object
         */
        SendUserJSON.sendJSON(response, properties.getProperty("valide.msg"), properties.getProperty("valide.code"), properties.getProperty("content.type"), properties.getProperty("encoding.type"));
    }
}
