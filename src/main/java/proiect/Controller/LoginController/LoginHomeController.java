package proiect.Controller.LoginController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiect.Data.Repository.Book.BookCategoryRepository;
import proiect.Data.Repository.Book.TagRepository;
import proiect.Data.Service.Book.BookServiceImpl;
import proiect.Data.Service.User.UserServiceImpl;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookCategory;
import proiect.Model.Book.BookTag;
import proiect.Model.Book.DTO.BookTagDTO;
import proiect.Model.User.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginHomeController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private TagRepository tagRepository;
    private User loggedUser;

    @GetMapping("/login")
    public String indexLogin(Model model) {
        model.addAttribute("user", new User());
        return "login/index";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findUserByUsernameAndPassword(username, password);
        if (user != null){
            model.addAttribute("user", user);
            loggedUser = user;
            return "redirect:/admin";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String indexRegister(Model model) {
        model.addAttribute("user", new User());
        return "register/index";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, Errors errors) {
        if(errors.hasErrors()){
            return "/register/index";
        }
        userService.save(user);
        return "redirect:/login";
    }


    @RequestMapping("/admin")
    public String index(Model model)
    {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        return "admin/index";
    }

    @GetMapping("/admin/books")
    public String displayAllBooks(@RequestParam(required = false) Integer categoryID, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if (categoryID == null){
            model.addAttribute("title","All Books");
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("userLogged", false);
        }else{
            Optional<BookCategory> result = bookCategoryRepository.findById(categoryID);
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Category ID: " + categoryID);
                model.addAttribute("userLogged", false);
            }else{
                BookCategory category = result.get();
                model.addAttribute("title", "Books in category: " + category.getName());
                model.addAttribute("books", category.getBooks());
                model.addAttribute("userLogged", false);
            }
        }
        return "admin/books/index";
    }


    @GetMapping("/admin/books/create")
    public String renderCreateEventForm(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "Create Book");
        model.addAttribute("book", new Book());
        model.addAttribute("bookCategories", bookCategoryRepository.findAll());
        return "admin/books/create";
    }
    @PostMapping("/admin/books/create")
    public String createBook(@ModelAttribute @Valid Book book, @RequestParam("imageFile") MultipartFile imageFile, Errors errors, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("bookCategories", bookCategoryRepository.findAll());
            model.addAttribute("userLogged", false);
            return "admin/books/create";
        }
        if (imageFile.isEmpty()) {
            book.getBookDetails().setImageData(null);
        } else {
            try {
                book.getBookDetails().setImageData(imageFile.getBytes());
            } catch (IOException ignored) {

            }
        }
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/admin/books/delete")
    public String displayDeleteBookForm(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "Delete Book");
        model.addAttribute("books", bookService.findAll());
        return "admin/books/delete";
    }
    @PostMapping("/admin/books/delete")
    public String processDeleteBooksForm(@RequestParam(required = false) int[] bookIDs, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(bookIDs != null){
            for(int id : bookIDs){
                bookService.deleteById(id);
            }
        }
        return "redirect:admin/books";
    }

    @GetMapping("/admin/books/add-tag")
    public String displayAddTagForm(@RequestParam Integer bookID, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        Optional<Book> result = bookService.findById(bookID);
        if(result.isEmpty()){
            model.addAttribute("title", "Invalid Book ID:" + bookID);
        }else {
            Book book = result.get();
            model.addAttribute("title", "Add BookTag to: " + book.getTitle());
            List<BookTag> tags = (List<BookTag>) tagRepository.findAll();
            tags.removeAll(book.getTags());
            model.addAttribute("tags", tags);
            BookTagDTO bookTagDTO = new BookTagDTO();
            bookTagDTO.setBook(book);
            model.addAttribute("bookTagDTO", bookTagDTO);
        }
        return "admin/books/add-tag";

    }
    @GetMapping("/admin/tags")
    public String displayAllTags(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "All Categories");
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/tags/index";
    }

    @PostMapping("/admin/books/add-tag")
    public String processAddTagForm(@ModelAttribute @Valid BookTagDTO bookTagDTO, Errors errors, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(!errors.hasErrors()){
            Book book = bookTagDTO.getBook();
            BookTag tag = bookTagDTO.getTag();
            if(!book.getTags().contains(tag)){
                book.addTag(tag);
                bookService.save(book);
            }
            return "redirect:detail?bookID=" + book.getID();
        }
        return "redirect:/admin/books/add-tag";
    }


    @GetMapping("admin/tags/create")
    public String renderCreateTagForm(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "Create Book");
        model.addAttribute("tag", new BookTag());
        return "admin/tags/create";
    }
    @PostMapping("admin/tags/create")
    public String createTag(@ModelAttribute @Valid BookTag tag, Errors errors, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(errors.hasErrors()){
            model.addAttribute("title", "Create BookTag");
            model.addAttribute("tags", tagRepository.findAll());
            return "admin/tags/create";
        }
        tagRepository.save(tag);
        return "redirect:admin/tags";
    }


    @GetMapping("/admin/categories")
    public String displayAllCategories(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        return "admin/categories/index";
    }

    @GetMapping("/admin/categories/create")
    public String renderCreateCategoryForm(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "Create Book");
        model.addAttribute("category", new BookCategory());
        return "admin/categories/create";
    }
    @PostMapping("/admin/categories/create")
    public String createEvent(@ModelAttribute("category") @Valid BookCategory category, Errors errors, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("categories", bookCategoryRepository.findAll());
            model.addAttribute("userLogged", false);
            return "admin/categories/create";
        }
        bookCategoryRepository.save(category);
        return "redirect:/categories";
    }
    @GetMapping("/admin/categories/delete")
    public String displayDeleteEventForm(Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        model.addAttribute("title", "Delete Book");
        model.addAttribute("categories", bookCategoryRepository.findAll());
        return "admin/categories/delete";
    }
    @PostMapping("/admin/categories/delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] categoryIDs, Model model) {
        if(loggedUser == null) {
            model.addAttribute("user", new User());
            model.addAttribute("userLogged", false);
            return "redirect:/login";
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("userLogged", true);
        if(categoryIDs != null){
            for(int id : categoryIDs){
                bookCategoryRepository.deleteById(id);
            }
        }
        return "redirect:/admin/categories";
    }
}
