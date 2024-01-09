package com.example.tutorial.Controller;

import com.example.tutorial.Data.EventCategoryRepository;
import com.example.tutorial.Model.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("eventCategories")
public class EventCategoryController {
    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "eventCategories/index";
    }
    @GetMapping("create")
    public String renderCreateCategoryForm(Model model) {
        model.addAttribute("title", "Create Book");
        model.addAttribute("category", new EventCategory());
        return "eventCategories/create";
    }
    @PostMapping("create")
    public String createEvent(@ModelAttribute("category") @Valid EventCategory category, Errors errors, Model model) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("categories", eventCategoryRepository.findAll());
            return "eventCategories/create";
        }
        eventCategoryRepository.save(category);
        return "redirect:/eventCategories";
    }
    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Book");
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "eventCategories/delete";
    }
    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] categoryIDs) {
        if(categoryIDs != null){
            for(int id : categoryIDs){
                eventCategoryRepository.deleteById(id);
            }
        }
        return "redirect:/eventCategories";
    }


}
