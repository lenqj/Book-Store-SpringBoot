package proiect.User.Controller.Admin.Books;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Book.Service.BookCategoryService;
import proiect.Book.Service.BookService;
import proiect.Book.Service.TagService;
import proiect.DTO.UserDto;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookCategory;
import proiect.Model.Book.BookTag;
import proiect.DTO.BookTagDTO;
import proiect.Book.Repository.TagRepository;
import proiect.User.Service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBooksController {
    private final BookService bookService;
    private final BookCategoryService bookCategoryService;
    private final TagService tagService;
    private final UserService userService;
    @GetMapping()
    public String displayAllBooks(@RequestParam(required = false) Integer categoryID, Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Books");
        model.addAttribute("headertext", "View all books!");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if (categoryID == null){
            model.addAttribute("title","All Books");
            model.addAttribute("books", bookService.findAll());
        }else{
            Optional<BookCategory> result = bookCategoryService.findById(categoryID);
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
        model.addAttribute("sitetitle", "LP - Create Book");
        model.addAttribute("headertext", "Create book!");
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Create Book");
        model.addAttribute("book", new Book());
        model.addAttribute("bookCategories", bookCategoryService.findAll());
        return "admin/books/create";
    }
    @PostMapping("/create")
    public String createBook(@ModelAttribute @Valid Book book, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, Errors errors, Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Book");
            model.addAttribute("bookCategories", bookCategoryService.findAll());
            return "admin/books/create";
        }
        if(imageFile != null){
            try {
                book.getBookDetails().setImageData(imageFile.getBytes());
            } catch (IOException ignored) {

            }
        }
        bookService.save(book);
        return "redirect:/admin/books";
    }
    @GetMapping("/delete")
    public String displayDeleteBookForm(Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Delete Book");
        model.addAttribute("headertext", "Delete books!");
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
        return "redirect:/admin/books";
    }

    @GetMapping("/add-tag")
    public String displayAddTagForm(@RequestParam Integer bookID, Model model, Authentication authentication){
        model.addAttribute("sitetitle", "LP - Add Tag Book");
        model.addAttribute("headertext", "Add Tag Book!");
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
            List<BookTag> tags = (List<BookTag>) tagService.findAll();
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
            return "redirect:/admin/books/detail?bookID=" + book.getID();
        }
        return "redirect:/admin/books/add-tag";
    }

    @RequestMapping("/update/{bookID}")
    public String displayBookUpdate(@PathVariable("bookID") int bookID, Model model) {
        model.addAttribute("headertext", "Update Book!");
        model.addAttribute("bookCategories", bookCategoryService.findAll());
        model.addAttribute("sitetitle", "LP - Update Book");
        Optional<Book> result = bookService.findById(bookID);
        if(result.isEmpty()){
            return "redirect: /admin/books";
        }
        Book book = result.get();
        model.addAttribute("book", book);
        return "/admin/books/update";
    }
    @RequestMapping("/update")
    public String createBook(@ModelAttribute Book book, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        /*Optional<Book> newBook = bookService.findById(book.getID());
        if(newBook.isPresent()) {
            if (imageFile != null) {
                try {
                    newBook.get().getBookDetails().setImageData(imageFile.getBytes());
                } catch (IOException ignored) {

                }
            }
            bookService.save(newBook.get());
            bookService.flush();
        }*/
        bookService.save(book);
        return "redirect:/admin/books";
    }
}
