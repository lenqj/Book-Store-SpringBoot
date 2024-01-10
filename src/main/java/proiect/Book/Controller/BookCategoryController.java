package proiect.Book.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.DTO.UserDto;
import proiect.User.Service.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("categories")
public class BookCategoryController {
    private final BookCategoryRepository bookCategoryRepository;
    private final UserService userService;

    @GetMapping
    public String displayAllCategories(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        return "categories/index";
    }


}
