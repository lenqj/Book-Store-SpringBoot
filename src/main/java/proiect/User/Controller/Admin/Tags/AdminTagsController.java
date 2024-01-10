package proiect.User.Controller.Admin.Tags;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookService;
import proiect.Book.Service.BookServiceImpl;
import proiect.DTO.UserDto;
import proiect.Model.Book.BookTag;
import proiect.Tag.Repository.TagRepository;
import proiect.User.Service.UserService;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")

public class AdminTagsController {
    private final BookService bookService;
    private final BookCategoryRepository bookCategoryRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    @GetMapping()
    public String displayAllTags(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Categories");
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/tags/index";
    }
    @GetMapping("/create")
    public String renderCreateTagForm(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Create Book");
        model.addAttribute("tag", new BookTag());
        return "admin/tags/create";
    }
    @PostMapping("/create")
    public String createTag(@ModelAttribute @Valid BookTag tag, Errors errors, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if(errors.hasErrors()){
            model.addAttribute("title", "Create BookTag");
            model.addAttribute("tags", tagRepository.findAll());
            return "admin/tags/create";
        }
        tagRepository.save(tag);
        return "redirect:/admin/tags";
    }
}
