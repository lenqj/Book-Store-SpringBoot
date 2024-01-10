package proiect.LoginController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookService;
import proiect.DTO.UserDto;
import proiect.Model.RegistrationRequest;
import proiect.Tag.Repository.TagRepository;
import proiect.Book.Service.BookServiceImpl;
import proiect.User.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginHomeController {

    private final BookService bookService;
    private final UserService userService;
    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("books", bookService.findAll());
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "login/index";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login/index";
    }

    @GetMapping("/register")
    public String register(@RequestParam(value="registrationSuccess", required = false) String success, Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("registrationSuccess", success);
        model.addAttribute("user", new RegistrationRequest());

        return "register/index";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") RegistrationRequest registrationRequest, RedirectAttributes redirectAttributes){

        UserDto userDto = userService.registerUser(registrationRequest);

        redirectAttributes.addAttribute("registrationSuccess", "Success");

        return "redirect:/register";
    }


    @RequestMapping("/admin")
    public String index(Model model, Authentication authentication)
    {
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        return "admin/index";
    }


}
