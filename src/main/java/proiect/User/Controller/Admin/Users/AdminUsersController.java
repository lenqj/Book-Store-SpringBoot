package proiect.User.Controller.Admin.Users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import proiect.DTO.UserDto;
import proiect.Mapper.RoleMapper;
import proiect.Model.User.User;
import proiect.User.Repository.UserRoleRepository;
import proiect.User.Service.UserRoleService;
import proiect.User.Service.UserService;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    @GetMapping()
    public String displayAllUsers(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Users");
        model.addAttribute("headertext", "View all users!");
        model.addAttribute("users", userService.findAll());
        return "admin/users/index";
    }

    @GetMapping("/create")
    public String displayCreateUserForm(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Create User");
        model.addAttribute("headertext", "Create user!");
        model.addAttribute("title", "Create User");
        model.addAttribute("user", new User());
        model.addAttribute("roles", userRoleService.getAllRoles());
        return "admin/users/create";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute @Valid User user, @RequestParam String role, Errors errors, Model model, Authentication authentication){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create User");
            model.addAttribute("roles", userRoleService.getAllRoles());
            return "admin/users/create";
        }
        user.setRoles(Collections.singletonList(userRoleService.findByName(role)));
        userService.createUser(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/delete")
    public String displayDeleteUserForm(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Delete User");
        model.addAttribute("headertext", "Delete users!");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Delete User");
        model.addAttribute("users", userService.findAll());
        return "admin/users/delete";
    }
    @PostMapping("/delete")
    public String processDeleteUsersForm(@RequestParam(required = false) int[] userIDs) {
        if(userIDs != null){
            for(int id : userIDs){
                userService.deleteById(id);
            }
        }
        return "redirect:/admin/users";
    }
}
