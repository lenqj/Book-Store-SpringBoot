package proiect.User.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import proiect.Book.Service.BookService;
import proiect.DTO.UserDto;
import proiect.Model.User.RegistrationRequest;
import proiect.User.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginHomeController {

    private final BookService bookService;
    private final UserService userService;
    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Home");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("books", bookService.findAll());
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("sitetitle", "LP - Login");
        return "login/index";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("sitetitle", "LP - Error");
        model.addAttribute("loginError", true);
        return "login/index";
    }

    @GetMapping("/register")
    public String register(@RequestParam(value="registrationSuccess", required = false) String success, Model model){
        model.addAttribute("sitetitle", "LP - Register");
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


    @GetMapping("/admin")
    public String index(Model model, Authentication authentication)
    {
        model.addAttribute("sitetitle", "LP - Admin");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
            model.addAttribute("headertext", "Welcome back, " + userDto.username() + "!");
        }
        return "admin/index";
    }
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication)
    {
        model.addAttribute("sitetitle", "LP - Admin");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
            model.addAttribute("headertext", "Profile - " + userDto.username());
        }
        return "profile/index";
    }

}
