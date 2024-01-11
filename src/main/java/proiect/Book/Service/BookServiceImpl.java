package proiect.Book.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proiect.Book.Repository.BookRepository;
import proiect.Model.Book.Book;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer ID) {
        bookRepository.deleteById(ID);
    }

    @Override
    public Optional<Book> findById(Integer ID) {
        return bookRepository.findById(ID);
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void flush() {
        bookRepository.flush();
    }
}
