package proiect.Book.Service;

import proiect.Model.Book.Book;
import proiect.Model.Book.BookCategory;

import java.util.Optional;

public interface BookCategoryService {
    void save(BookCategory bookCategory);
    void deleteById(Integer ID);
    Optional<BookCategory> findById(Integer ID);
    Iterable<BookCategory> findAll();
    void flush();
}
