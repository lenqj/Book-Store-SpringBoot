package proiect.Book.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookCategoryService;
import proiect.DTO.UserDto;
import proiect.User.Service.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("categories")
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;
    private final UserService userService;

    @GetMapping
    public String displayAllCategories(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Category Books");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", bookCategoryService.findAll());

        return "categories/index";
    }


}
