package ru.mail.service.util;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.mail.service.impl.FileStructureDAOImpl;
import ru.mail.service.model.AjaxResponseUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Denis Monich
 * this class create json object to sent data about user authentication
 */
public class SendUserJSON {
    private static final Logger logger = Logger.getLogger(SendUserJSON.class);

    /**
     *
     * @param resp
     * @param msg text message about user validation result
     * @param code text code about user validation result
     * @param CONTENT_TYPE set json type
     * @param ENCODING_TYPE set UTF-8 encoding type
     * @throws IOException can be throw during resolving response Writer
     */
    public static void sendJSON(HttpServletResponse resp, String msg, String code,String CONTENT_TYPE, String ENCODING_TYPE) throws IOException {
        logger.error("sending UserJSON");
        AjaxResponseUser result = new AjaxResponseUser(msg, code);
        String json = new Gson().toJson(result);
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING_TYPE);
        resp.getWriter().write(json);
    }
}
