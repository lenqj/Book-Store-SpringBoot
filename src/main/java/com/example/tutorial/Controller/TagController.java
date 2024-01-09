package com.example.tutorial.Controller;

import com.example.tutorial.Data.TagRepository;
import com.example.tutorial.Model.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagRepository.findAll());
        return "tags/index";
    }
    @GetMapping("create")
    public String renderCreateCategoryForm(Model model) {
        model.addAttribute("title", "Create Book");
        model.addAttribute("tag", new Tag());
        return "tags/create";
    }
    @PostMapping("create")
    public String createEvent(@ModelAttribute("category") @Valid Tag tag, Errors errors, Model model) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create BookTag");
            model.addAttribute("tags", tagRepository.findAll());
            return "tags/create";
        }
        tagRepository.save(tag);
        return "redirect:/tags";
    }



}
