package ru.mail.service.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.controller.LoginController;
import ru.mail.service.FileStructureDAO;
import ru.mail.service.model.FileStructureAjax;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Denis Monich
 * this class realise all logic to get full directory structure
 */
@Service
public class FileStructureDAOImpl implements FileStructureDAO {
    private static final Logger logger = Logger.getLogger(FileStructureDAOImpl.class);

    public FileStructureDAOImpl() {
    }

    /**
     * @param path full path of request directory
     * @return List of FileStructure files
     */
    @Override
    public List<FileStructureAjax> getStructure(String path) {
        /*
        getting file by path
        */
        File rootDirectory = new File(path);
        List<FileStructureAjax> returnFiles = new ArrayList<FileStructureAjax>();
        /*
        checking is file folder
         */
        logger.error("coming to path: " + path);
        if (rootDirectory.isDirectory()) {
            logger.error("directory is folder");
            /*
            getting files inside folder
             */
            File[] listOfFiles = rootDirectory.listFiles();
            if (listOfFiles == null) {
                logger.error("no files in folder");
                return returnFiles;
            } else {
                /*
                putting files in list: name, size, is Directory
                 */
                logger.error("in folder: " + listOfFiles.length + " files");
                for (File listOfFile : listOfFiles) {
                    FileStructureAjax fileStructureAjax = new FileStructureAjax();
                    fileStructureAjax.setFileName(listOfFile.getName());
                    /*
                    checking is file directory
                     */
                    if (listOfFile.isFile()) {
                        fileStructureAjax.setSize(BigInteger.valueOf(listOfFile.length()));
                        fileStructureAjax.setDirectory(false);
                    } else {
                        /*
                        if directory - resolve recursion function
                         */
                        fileStructureAjax.setSize(getSize(listOfFile.getPath()));
                        fileStructureAjax.setDirectory(true);
                    }
                    returnFiles.add(fileStructureAjax);
                }
            }
        }
        /*
        sorting files in list: 1.By is Directory; 2.By name
         */
        logger.error("sorting of files list");
        returnFiles.sort((p1, p2) -> {
            if (p2.getDirectory().compareTo(p1.getDirectory()) == 0) {
                return p1.getFileName().compareToIgnoreCase(p2.getFileName());
            } else {
                return p2.getDirectory().compareTo(p1.getDirectory());
            }
        });
        return returnFiles;
    }

    /**
     * @param path full path of request directory
     * @return directory path 1 level higher of current
     */
    @Override
    public String getBackButtomPath(String path) {

        /*
        getting directory to go to up folder
         */
        File rootDirectory = new File(path);
        logger.error("parent directory: " + rootDirectory.getParent());
        return rootDirectory.getParent();
    }

    /**
     * @param path full path of request directory
     * @return path with valid file separator
     */
    @Override
    public String getValidPath(String path) {
        File rootDirectory = new File(path);
        return rootDirectory.getPath();
    }

    /**
     * @param fullPath full path of request directory
     * @return size of file by full path
     */
    private BigInteger getSize(String fullPath) {
        /*
        recursion function for counting size of folder
         */
        BigInteger size = BigInteger.ZERO;
        File rootDirectory = new File(fullPath);
        if (rootDirectory.isDirectory()) {
            File[] listOfFiles = rootDirectory.listFiles();
            if (listOfFiles == null) {
            } else {
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        size = size.add(BigInteger.valueOf(listOfFile.length()));
                    } else {
                        size = size.add(getSize(listOfFile.getPath()));
                    }
                }
            }
        }
        return size;
    }

}

