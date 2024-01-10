package proiect.User.Controller.Admin.Categories;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookService;
import proiect.Book.Service.BookServiceImpl;
import proiect.DTO.UserDto;
import proiect.Model.Book.BookCategory;
import proiect.Tag.Repository.TagRepository;
import proiect.User.Service.UserService;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")

public class AdminCategoriesController {
    private final BookService bookService;
    private final BookCategoryRepository bookCategoryRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    @GetMapping()
    public String displayAllCategories(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        return "admin/categories/index";
    }

    @GetMapping("/create")
    public String renderCreateCategoryForm(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Create Book");
        model.addAttribute("category", new BookCategory());
        return "admin/categories/create";
    }
    @PostMapping("/create")
    public String createEvent(@ModelAttribute("category") @Valid BookCategory category, Errors errors, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("categories", bookCategoryRepository.findAll());
            model.addAttribute("userLogged", false);
            return "admin/categories/create";
        }
        bookCategoryRepository.save(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("/delete")
    public String displayDeleteEventForm(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Delete Book");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        return "admin/categories/delete";
    }
    @PostMapping("/delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] categoryIDs, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if(categoryIDs != null){
            for(int id : categoryIDs){
                bookCategoryRepository.deleteById(id);
            }
        }
        return "redirect:/admin/categories";
    }
}
