package com.example.payitforward.controllers;

import com.example.payitforward.models.User;
import com.example.payitforward.models.data.UserDao;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @Autowired
    UserDao userDao;

    //if user is logged in, allows them to view a template where they can upload a photo
    @GetMapping("upload")
    public String uploadFile(HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }
        return "upload";
    }

    //processes the user's uploaded image
    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {

        //if the file the user is trying to upload is empty, return an error message
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "upload";
        }

        //get the ID of the current user as a string -- will be used to name the directory
        User currentUser = (User) session.getAttribute("loggedInUser");
        String currentId = String.valueOf(currentUser.getId());

        //create a new folder within upload-dir corresponding to the user's ID to hold their photo
        File dir = new File("src/main/resources/static/upload-dir" + File.separator + currentId);

        boolean successful = dir.mkdir();
        if (successful) {
            // creating the directory succeeded
            System.out.println("directory was created successfully");
        } else {
            // creating the directory failed
            System.out.println("failed trying to create the directory");
        }

        //TODO: refactor: store reference to photo location in DB

        //before adding a new picture, make sure there are no existing pictures in the folder,
        //so each user will have only one picture
        for(File aFile: dir.listFiles())
            if (!aFile.isDirectory())
                aFile.delete();

        try {
            //get the file
            byte[] bytes = file.getBytes();

            //save the file in the previously created folder
            Path path = Paths.get("src/main/resources/static/upload-dir" + File.separator + currentId +
                    File.separator + file.getOriginalFilename());

            Files.write(path, bytes);

            String pathString = path.toString();

            currentUser.setImagePath(pathString);
            userDao.save(currentUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/profile/myprofile";
    }

}