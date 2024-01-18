package proiect.Book.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import proiect.Book.Service.BookService;
import proiect.Book.Service.ShoppingCartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect.Model.Book.Book;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    private final BookService bookService;

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("books", shoppingCartService.getBooksInCart().entrySet());
        model.addAttribute("total", shoppingCartService.getTotal().toString());
        return "cart/index";
    }
    @RequestMapping("/cart/add-to-cart")
    public String addBookToCart(Model model, @RequestParam(required = false) Integer bookID) {
        Optional<Book> book = bookService.findById(bookID);
        if(book.isPresent()) {
            shoppingCartService.addBook(book.get());
            model.addAttribute("books", shoppingCartService.getBooksInCart().entrySet());
            model.addAttribute("total", shoppingCartService.getTotal().toString());
            return "redirect:/cart";
        }
        return "redirect:/books";
    }
    @RequestMapping("/cart/remove-from-cart")
    public String removeBookFromCart(Model model, @RequestParam(required = false) Integer bookID) {
        Optional<Book> book = bookService.findById(bookID);
        if(book.isPresent()) {
            shoppingCartService.removeBook(book.get());
            model.addAttribute("books", shoppingCartService.getBooksInCart().entrySet());
            model.addAttribute("total", shoppingCartService.getTotal().toString());
            return "redirect:/cart";
        }
        return "redirect:/books";
    }
    @RequestMapping("/cart/checkout")
    public String checkout() {
        shoppingCartService.checkout();
        return "redirect:/cart";
    }
}
