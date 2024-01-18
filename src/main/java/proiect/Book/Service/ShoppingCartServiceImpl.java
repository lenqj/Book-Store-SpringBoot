package proiect.Book.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import proiect.Book.Repository.BookRepository;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private final BookRepository bookRepository;
    private Map<Book, Integer> books = new HashMap<>();

    public ShoppingCartServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        if (books.containsKey(book)) {
            books.replace(book, books.get(book) + 1);
        } else {
            books.put(book, 1);
        }
    }

    public void removeBook(Book book) {
        if (books.containsKey(book)) {
            if (books.get(book) > 1)
                books.replace(book, books.get(book) - 1);
            else if (books.get(book) == 1) {
                books.remove(book);
            }
        }
    }

    public Map<Book, Integer> getBooksInCart() {
        return Collections.unmodifiableMap(books);
    }

    public void checkout() {
        Optional<Book> book;
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            book = bookRepository.findById(entry.getKey().getID());
            if(book.isPresent()) {
                Book bookResult = book.get();
                if (bookResult.getBookDetails().getStock() > entry.getValue()) {
                    entry.getKey().getBookDetails().setStock(bookResult.getBookDetails().getStock() - entry.getValue());
                    bookRepository.save(entry.getKey());
                }
            }
        }
        bookRepository.flush();
        books.clear();
    }

    @Override
    public Long getTotal() {
        return books.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getBookDetails().getPrice() * entry.getValue())
                .sum();
    }
}
