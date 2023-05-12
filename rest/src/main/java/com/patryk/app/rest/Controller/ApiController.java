package com.patryk.app.rest.Controller;

import com.patryk.app.rest.Model.User;
import com.patryk.app.rest.Model.UserRegisterFormData;
import com.patryk.app.rest.Repository.FilesRepository;
import com.patryk.app.rest.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.patryk.app.rest.Model.FileData;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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
        model.addAttribute("userRegisterFormData", new UserRegisterFormData());
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

    public boolean isEmailAlreadyInUse(String email) {
        boolean emailUsed = false;
        for(User user : usersRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                emailUsed = true;
            }
        }
        return emailUsed;
    }

    public String encodePassword(String password) {

        return "";
    }

    @PostMapping(value = "/process_register")
    public String handleRegisterData(@ModelAttribute(value="userRegisterFormData")
                                         UserRegisterFormData userRegisterFormData) {

        if(isEmailAlreadyInUse(userRegisterFormData.getEmail())) {
            return "registerForm";
        }
        else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(userRegisterFormData.getPassword());

            User user = new User();
            user.setPassword(encodedPassword);
            user.setEmail(userRegisterFormData.getEmail());
            user.setName(userRegisterFormData.getName());
            usersRepository.save(user);

            return "registerSuceeded";
        }
    }



    /*@PostMapping(value = "/fileSystem")
    public Long uploadImage(@RequestParam MultipartFile multipartFile) throws IOException {
        String filePath = FOLDER_PATH + multipartFile.getOriginalFilename();
        FileData fileData = new FileData();
        fileData.setName(multipartFile.getName());
        fileData.setType(multipartFile.getContentType());
        fileData.setFilePath(filePath);
        filesRepository.save(fileData);

        multipartFile.transferTo(new File(filePath));

        return fileData.getId();
    }*/

}
