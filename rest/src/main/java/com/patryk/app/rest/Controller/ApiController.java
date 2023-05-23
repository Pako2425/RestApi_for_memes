package com.patryk.app.rest.Controller;

import com.patryk.app.rest.Model.User;
import com.patryk.app.rest.Model.UserRegisterFormData;
import com.patryk.app.rest.Model.UserRole;
import com.patryk.app.rest.Repository.FilesRepository;
import com.patryk.app.rest.Repository.UsersRepository;
import com.patryk.app.rest.Service.RegisterRequest;
import com.patryk.app.rest.Service.RegisterService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ApiController {
    
    private final FilesRepository filesRepository;
    private final UsersRepository usersRepository;
    private final RegisterService registerService;
    private final String FOLDER_PATH = "C:/Users/Lenovo/Desktop/files/";

    @GetMapping(value = "/")
    public String viewHomePage() {
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

    public String encodePassword(String password) {

        return "";
    }

    public boolean isRegisterDataCorrect(UserRegisterFormData userRegisterFormData) {
        boolean isEmailAlreadyInUse = false;
        boolean isNameAlreadyInUse = false;
        boolean isPasswordCorrect = false;

        for(User user : usersRepository.findAll()) {
            if (user.getEmail().equals(userRegisterFormData.getEmail())) {
                isEmailAlreadyInUse = true;
            }
            if (user.getUsername().equals(userRegisterFormData.getName())) {
                isNameAlreadyInUse = true;
            }
            if (userRegisterFormData.getPassword().equals(userRegisterFormData.getRepeatPassword())) {
                isPasswordCorrect = true;
            }
        }

        if(!isEmailAlreadyInUse && !isNameAlreadyInUse && isPasswordCorrect) {
            return true;
        }
        else {
            return false;
        }
    }

    @PostMapping(value = "/process_register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return "register succesful";
    }

    @PostMapping(value = "/process_register")
    public String handleRegisterData(@ModelAttribute(value="userRegisterFormData")
                                         UserRegisterFormData userRegisterFormData) {

        if(isRegisterDataCorrect(userRegisterFormData)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(userRegisterFormData.getPassword());

            User user = new User(userRegisterFormData.getName(), userRegisterFormData.getEmail(), encodedPassword, UserRole.USER);
            usersRepository.save(user);

            return "registerSuceeded";
        }
        else {
            return "registerForm";
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
