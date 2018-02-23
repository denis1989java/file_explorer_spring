package ru.mail.controller;


import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mail.service.FileStructureDAO;
import ru.mail.service.jsonview.Views;
import ru.mail.service.model.AjaxResponseDirectory;
import ru.mail.service.model.FileStructureAjax;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * @author Denis Monich
 * this class implements view of directories's structure
 */
@Controller
public class ExplorerController {

    private final FileStructureDAO fileStructureDAO;
    private final Properties properties;
    private static final Logger logger = Logger.getLogger(ExplorerController.class);


    @Autowired
    public ExplorerController(FileStructureDAO fileStructureDAO, Properties properties) {
        this.fileStructureDAO = fileStructureDAO;
        this.properties = properties;
    }

    /**
     * @return name of jsp page where view data
     */
    @RequestMapping(value = "/explorer", method = RequestMethod.GET)
    public String showRootDirectory() {
        logger.error("coming to explorer GET");
        return "directory";
    }

    /**
     * @param data json file with 1 or 2 fields (other is null) for getting new directory structure
     * @return json object with 4 or 5 fields (other null) for writing directory structure on UI
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = {"/explorer"}, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public @ResponseBody
    AjaxResponseDirectory showDirectory(@RequestBody AjaxResponseDirectory data) {
        logger.error("coming to show directory POST");
        String path = "";
        if (data == null) {
            path = properties.getProperty("rootDirectory");
        } else {
            if (data.getParent() == null) {
                path = properties.getProperty("rootDirectory");
            } else {
                if (data.getPath() != null) {
                    path = data.getParent() + File.separator + data.getPath();
                } else {
                    path = data.getParent();
                }
            }
        }
        logger.error("path from UI: " + path);

        /*
          getting list of files from directory by path
         */
        List<FileStructureAjax> files = fileStructureDAO.getStructure(path);

        /*
          getting directory path to go to up folder
         */
        String backButton = fileStructureDAO.getBackButtomPath(path);
        String validPath = fileStructureDAO.getValidPath(path);
        AjaxResponseDirectory result;
        /*
        checking is directory is empty and send different json files
         */
        if (files.isEmpty()) {
            result = new AjaxResponseDirectory("List is empty", "200", files, backButton, validPath);
        } else {
            result = new AjaxResponseDirectory("204", files, backButton, validPath);
        }
        return result;
    }
}
