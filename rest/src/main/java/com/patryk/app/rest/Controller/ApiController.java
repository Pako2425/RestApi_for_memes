package com.patryk.app.rest.Controller;

import com.patryk.app.rest.Model.User;
import com.patryk.app.rest.Model.UserRegisterFormData;
import com.patryk.app.rest.Repository.FilesRepository;
import com.patryk.app.rest.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.patryk.app.rest.Model.FileData;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Controller
public class ApiController {

    @Autowired
    FilesRepository filesRepository;

    @Autowired
    UsersRepository usersRepository;

    private final String FOLDER_PATH = "C:/Users/Lenovo/Desktop/files/";

    @GetMapping(value = "/")
    public String viewHomePage() {
        System.out.println("Welcome on home page :)");
        return "homePage";
    }

    @GetMapping(value = "/register")
    public String viewRegisterPage(Model model) {
        //User user = new User();
        //model.addAttribute("user", user);
        System.out.println("Welcome on register page :)");
        return "registerForm";
    }

    @GetMapping(value = "/sign_in")
    public String viewSignInPage()
    {
        return "signInForm";
    }

    @GetMapping(value = "/terms_and_conditions")
    public String viewTermsAndConditionsPage() {
        return "termsAndConditions";
    }

    @GetMapping(value = "/fileSystem/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadImage(@PathVariable Long fileId) throws IOException {
        Optional<FileData> fileData = filesRepository.findById(fileId);
        String filePath = fileData.get().getFilePath();
        byte[] meme = Files.readAllBytes(new File(filePath).toPath());

        return meme;
    }

    @PostMapping(value = "/process_register")
    public String handleRegisterData(@ModelAttribute UserRegisterFormData user) {

        System.out.println(user.getPassword());
        if(user.getPassword().compareTo(user.getRepeatPassword())==0) {
            return "homePage";
        }
        else {
            return "registerForm";
        }

    }

    @PostMapping(value = "/fileSystem")
    public Long uploadImage(@RequestParam MultipartFile multipartFile) throws IOException {
        String filePath = FOLDER_PATH + multipartFile.getOriginalFilename();
        FileData fileData = new FileData();
        fileData.setName(multipartFile.getName());
        fileData.setType(multipartFile.getContentType());
        fileData.setFilePath(filePath);
        filesRepository.save(fileData);

        multipartFile.transferTo(new File(filePath));

        return fileData.getId();
    }
}
