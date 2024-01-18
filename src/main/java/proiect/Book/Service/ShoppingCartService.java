package proiect.Book.Service;

import proiect.Model.Book.Book;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartService {

    void addBook(Book book);

    void removeBook(Book book);

    Map<Book, Integer> getBooksInCart();

    void checkout();

    Long getTotal();
}

