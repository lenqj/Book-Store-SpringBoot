package proiect.Tag.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proiect.DTO.UserDto;
import proiect.Tag.Repository.TagRepository;
import proiect.User.Service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("tags")
public class TagController {
    private final TagRepository tagRepository;
    private final UserService userService;

    @GetMapping
    public String displayAllCategories(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagRepository.findAll());
        return "tags/index";
    }


}
