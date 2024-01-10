package proiect.User.Controller.Admin.Books;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookService;
import proiect.Book.Service.BookServiceImpl;
import proiect.DTO.UserDto;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookCategory;
import proiect.Model.Book.BookTag;
import proiect.Model.Book.DTO.BookTagDTO;
import proiect.Tag.Repository.TagRepository;
import proiect.User.Service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBooksController {
    private final BookService bookService;
    private final BookCategoryRepository bookCategoryRepository;
    private final TagRepository tagRepository;
    private final UserService userService;

    @GetMapping()
    public String displayAllBooks(@RequestParam(required = false) Integer categoryID, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if (categoryID == null){
            model.addAttribute("title","All Books");
            model.addAttribute("books", bookService.findAll());
        }else{
            Optional<BookCategory> result = bookCategoryRepository.findById(categoryID);
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Category ID: " + categoryID);
            }else{
                BookCategory category = result.get();
                model.addAttribute("title", "Books in category: " + category.getName());
                model.addAttribute("books", category.getBooks());
            }
        }
        return "admin/books/index";
    }


    @GetMapping("/create")
    public String renderCreateEventForm(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
            System.out.println("User Authorities: " + authentication.getAuthorities());
        }
        model.addAttribute("title", "Create Book");
        model.addAttribute("book", new Book());
        model.addAttribute("bookCategories", bookCategoryRepository.findAll());
        return "admin/books/create";
    }
    @PostMapping("/create")
    public String createBook(@ModelAttribute @Valid Book book, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, Errors errors, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
            System.out.println("User Authorities: " + authentication.getAuthorities());
        }
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("bookCategories", bookCategoryRepository.findAll());
            return "admin/books/create";
        }
        if(imageFile != null){
            try {
                book.getBookDetails().setImageData(imageFile.getBytes());
            } catch (IOException ignored) {

            }
        }
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/delete")
    public String displayDeleteBookForm(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Delete Book");
        model.addAttribute("books", bookService.findAll());
        return "admin/books/delete";
    }
    @PostMapping("/delete")
    public String processDeleteBooksForm(@RequestParam(required = false) int[] bookIDs, Model model) {
        if(bookIDs != null){
            for(int id : bookIDs){
                bookService.deleteById(id);
            }
        }
        return "redirect:/books";
    }

    @GetMapping("/add-tag")
    public String displayAddTagForm(@RequestParam Integer bookID, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
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
    @PostMapping("/add-tag")
    public String processAddTagForm(@ModelAttribute @Valid BookTagDTO bookTagDTO, Errors errors, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if(!errors.hasErrors()){
            Book book = bookTagDTO.getBook();
            BookTag tag = bookTagDTO.getTag();
            if(!book.getTags().contains(tag)){
                book.addTag(tag);
                bookService.save(book);
            }
            return "redirect:detail?bookID=" + book.getID();
        }
        return "redirect:/add-tag";
    }
}
