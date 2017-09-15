package net.antra.controller;

import net.antra.entity.MyFile;
import net.antra.exception.FileUploadException;
import net.antra.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IAN on 2017/9/15.
 */
@RestController
public class FileUploadController {
    @Autowired
    FileService fs;

    private final String dir="files";
    @RequestMapping(value="/fileupload",method= RequestMethod.POST)
    public MyFile uploadFile(@RequestParam("file") MultipartFile inputfile)throws IOException{
        String path=(new File(dir)).getAbsolutePath() + "/" + inputfile.getOriginalFilename();
        if(fs.checkExist(path)){
            return null;
        }else{
            MyFile mf=new MyFile(inputfile.getOriginalFilename(),new Date(),inputfile.getSize(),path);
            fs.saveFile(inputfile,mf);
            return mf;
        }
    }
    @RequestMapping(value = "/viewfile/{id}", method = RequestMethod.GET)
    public MyFile getFileInfo(@PathVariable Integer id) throws FileUploadException {
        if (fs.getFile(id) == null) throw new FileUploadException("File doesn't exist");
        else return fs.getFile(id);
    }

    @RequestMapping(value = "/viewfiles", method = RequestMethod.GET)
    public List<MyFile> getAllFiles() {
        return fs.getAllFiles();
    }
    //Handle the exception and return bad request
    @ExceptionHandler(value = FileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String showError(FileUploadException e) {
        return "File upload error : "+ e.getMessage();
    }

    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String showError(NumberFormatException e) {
        return "Invalid number : "+ e.getMessage();
    }
}

