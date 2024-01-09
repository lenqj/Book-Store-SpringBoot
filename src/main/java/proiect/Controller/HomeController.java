package proiect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proiect.Data.Repository.Book.BookRepository;

@Controller
public class HomeController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping()
    public String index(Model model)
    {
        model.addAttribute("title", "Home!");
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("userLogged", false);
        return "index";
    }
}
