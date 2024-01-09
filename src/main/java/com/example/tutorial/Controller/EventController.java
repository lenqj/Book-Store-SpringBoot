package com.example.tutorial.Controller;

import com.example.tutorial.Data.EventCategoryRepository;
import com.example.tutorial.Data.EventRepository;
import com.example.tutorial.Data.TagRepository;
import com.example.tutorial.Model.DTO.EventTagDTO;
import com.example.tutorial.Model.Event;
import com.example.tutorial.Model.EventCategory;
import com.example.tutorial.Model.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryID, Model model) {
        if (categoryID == null){
        model.addAttribute("title","All Events");
        model.addAttribute("events",eventRepository.findAll());
        }else{
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryID);
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Category ID: " + categoryID);
            }else{
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }
        return "events/index";
    }
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Book");
        model.addAttribute("event", new Event());
        model.addAttribute("eventCategories", eventCategoryRepository.findAll());
        return "events/create";
    }
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event event, Errors errors, Model model) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            return "events/create";
        }
        eventRepository.save(event);
        return "redirect:/events";
    }
    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Book");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }
    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIDs) {
        if(eventIDs != null){
            for(int id : eventIDs){
                eventRepository.deleteById(id);
            }
        }
        return "redirect:/events";
    }

    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventID, Model model) {
        Optional<Event> result = eventRepository.findById(eventID);
        if(result.isEmpty()){
            model.addAttribute("title", "Invalid Book ID:" + eventID);

        }else{
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }
        return "events/detail";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventID, Model model) {
        Optional<Event> result = eventRepository.findById(eventID);
        if(result.isEmpty()){
            model.addAttribute("title", "Invalid Book ID:" + eventID);
        }else {
            Event event = result.get();
            model.addAttribute("title", "Add BookTag to: " + event.getName());
            List<Tag> tags = (List<Tag>) tagRepository.findAll();
            tags.removeAll(event.getTags());
            model.addAttribute("tags", tags);
            EventTagDTO eventTagDTO = new EventTagDTO();
            eventTagDTO.setEvent(event);
            model.addAttribute("eventTagDTO", eventTagDTO);
        }
        return "events/add-tag";
    }
    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTagDTO, Errors errors) {
        if(!errors.hasErrors()){
            Event event = eventTagDTO.getEvent();
            Tag tag = eventTagDTO.getTag();
            if(!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventID=" + event.getID();
        }
        return "redirect:add-tag";
    }



}
