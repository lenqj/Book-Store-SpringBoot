package proiect.Controller.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect.Data.Repository.Book.BookCategoryRepository;


@Controller
@RequestMapping("categories")
public class BookCategoryController {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        model.addAttribute("userLogged", false);
        return "categories/index";
    }


}
