package proiect.Book.Service;

import proiect.Model.Book.BookTag;

import java.util.Optional;

public interface TagService {
    void save(BookTag tag);
    void deleteById(Integer ID);
    Optional<BookTag> findById(Integer ID);
    Iterable<BookTag> findAll();
    void flush();
}
