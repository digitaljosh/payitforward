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

        //TODO: get user ID
        User currentUser = (User) session.getAttribute("loggedInUser");
        String currentId = String.valueOf(currentUser.getId());

        //TODO: create folder based on user ID
        File dir = new File("upload-dir" + File.separator + currentId);
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

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            Path path = Paths.get("upload-dir" + File.separator + currentId + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/profile/uploadStatus";
    }

    @GetMapping("profile/uploadStatus")
    public String uploadStatus() {
        return "profile/uploadStatus";
    }

}