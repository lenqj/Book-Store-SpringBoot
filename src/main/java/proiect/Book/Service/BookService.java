package proiect.Book.Service;


import proiect.Model.Book.Book;

import java.util.Optional;

public interface BookService {
    void save(Book book);
    void deleteById(Integer ID);
    Optional<Book> findById(Integer ID);
    Iterable<Book> findAll();
}
