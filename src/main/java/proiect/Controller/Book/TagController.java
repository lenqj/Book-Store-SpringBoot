package proiect.Controller.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proiect.Data.Repository.Book.TagRepository;


@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("userLogged", false);
        return "tags/index";
    }


}
