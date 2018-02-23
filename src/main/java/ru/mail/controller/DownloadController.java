package ru.mail.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * @author Denis Monich
 * this class implements file downloading from local disk
 */
@Controller
@RequestMapping(value = "/download")
public class DownloadController {

    private final Properties properties;
    private static final Logger logger = Logger.getLogger(DownloadController.class);

    private final ServletContext servletContext;

    @Autowired
    public DownloadController(Properties properties, ServletContext servletContext) {
        this.properties = properties;
        this.servletContext = servletContext;
    }

    /**
     *
     * @param path root path to file's folder
     * @param fname file's name
     * @param response if for sending answer to UI
     * @param request is for getting request from UI
     * @throws IOException can be throw during reading the {@link Properties} file
     */
    @RequestMapping(method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fullPath") String path, @RequestParam("fileName") String fname,
                             HttpServletResponse response, HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        logger.error("Coming file name: " + fname);
        String fileName1 = null;
        try {
            fileName1 = URLEncoder.encode(fname, properties.getProperty("encoding.type"));
            fileName1 = URLDecoder.decode(fileName1, properties.getProperty("decoding.type"));
        } catch (UnsupportedEncodingException e) {
            logger.error("encoding.name");
        }
        logger.error("Try to encode "+fileName1);
        byte bytes1[] = fname.getBytes("ISO-8859-1");
        String tryagain = new String(bytes1, "UTF-8");
        logger.error("Try to encode again "+tryagain);
        byte bytes2[] = fname.getBytes("UTF-8");
        String tryagain2 = new String(bytes2, "ISO-8859-1");
        logger.error("Try to encode again2 "+tryagain2);
        logger.error("coming to download GET");
        byte bytes[] = fname.getBytes("UTF-8");
        String name = new String(bytes, "UTF-8");
        logger.error("File name after Encoding: "+name);
        //encoding and decoding file name (for russian and other simbols)

        try {
            String fullPath=path+File.separator+name;
            logger.error("download fullpath: "+ fullPath);
            File file = new File(fullPath);
            String fileName = null;
            //encoding and decoding file name (for russian and other simbols)
            try {
                fileName = URLEncoder.encode(file.getName(), properties.getProperty("encoding.type"));
                fileName = URLDecoder.decode(fileName, properties.getProperty("decoding.type"));
            } catch (UnsupportedEncodingException e) {
                logger.error("encoding.name");
            }
            //reading thread of bytes - and writenning new file
            InputStream fis = null;
            fis = new FileInputStream(file);

            String mimeType = servletContext.getMimeType(file.getAbsolutePath());
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
        } catch (IOException e) {
            logger.error("reading.file");
            response.sendRedirect(request.getContextPath()+"/explorer?noFile=true");
        }
//        try {
//
//            String fullPath = path + File.separator + name;
//            System.out.println(fullPath+"     FULLPATH");
//            File file = new File(fullPath);
//            String fileName = null;
//            /*
//            encoding and decoding file name (for russian and other simbols)
//             */
//            try {
//                fileName = URLEncoder.encode(file.getName(), properties.getProperty("encoding.type"));
//                fileName = URLDecoder.decode(fileName, properties.getProperty("decoding.type"));
//            } catch (UnsupportedEncodingException e) {
//                logger.error("encoding.name");
//            }
//            InputStream inputStream = new FileInputStream(file);
//            response.setContentType(properties.getProperty("download.content.type"));
//            response.setCharacterEncoding(properties.getProperty("encoding.type"));
//            response.setHeader(properties.getProperty("download.header"), "attachment; filename=" + fileName);
//            IOUtils.copy(inputStream, response.getOutputStream());
//            logger.error("downloading the file");
//            response.flushBuffer();
//            inputStream.close();
//        } catch (IOException e) {
//            logger.error("reading.file");
//            response.sendRedirect(request.getContextPath()+properties.getProperty("explorer.exception.url"));        }
    }
}

