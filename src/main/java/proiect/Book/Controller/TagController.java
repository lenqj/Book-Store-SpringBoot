package proiect.Book.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proiect.Book.Service.TagService;
import proiect.DTO.UserDto;
import proiect.Book.Repository.TagRepository;
import proiect.User.Service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;
    private final UserService userService;

    @GetMapping
    public String displayAllCategories(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagService.findAll());
        return "tags/index";
    }


}
