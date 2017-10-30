package com.example.payitforward.controllers;

import com.example.payitforward.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://temp//";

    @GetMapping("profile/upload")
    public String index(HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }
        return "profile/upload";
    }

    @PostMapping("profile/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpSession session) {

        //get the ID of the current user
        User currentUser = (User) session.getAttribute("loggedInUser");
        String currentId = String.valueOf(currentUser.getId());

        //create a new folder within upload-dir corresponding to the user's ID to hold their photo
        File dir = new File("src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "static" + File.separator
                + File.separator + "upload-dir" + File.separator + currentId);
        boolean successful = dir.mkdir();
        if (successful) {
            // creating the directory succeeded
            System.out.println("directory was created successfully");
        } else {
            // creating the directory failed
            System.out.println("failed trying to create the directory");
        }


        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/profile/uploadStatus";
        }
        //TODO: limit file size
        //TODO: if user already has a picture, delete it before adding new one

        //TODO: change below so not flash
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //save the file in the previously created folder
            Path path = Paths.get("src" + File.separator + "main" + File.separator + "resources" +
                    File.separator + "static" +
                    File.separator + "upload-dir" + File.separator + currentId
                    + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: change so redirects to myprofile or view
        return "redirect:/profile/uploadStatus";
    }

    //TODO: prob don't need this
    @GetMapping("profile/uploadStatus")
    public String uploadStatus() {
        return "profile/uploadStatus";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}