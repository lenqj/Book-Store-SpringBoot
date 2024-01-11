package proiect.Book.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proiect.Book.Repository.BookCategoryRepository;
import proiect.Model.Book.BookCategory;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService{
    private final BookCategoryRepository bookCategoryRepository;

    @Override
    public void save(BookCategory bookCategory) {
        bookCategoryRepository.save(bookCategory);
    }

    @Override
    public void deleteById(Integer ID) {
        bookCategoryRepository.deleteById(ID);
    }

    @Override
    public Optional<BookCategory> findById(Integer ID) {
        return bookCategoryRepository.findById(ID);
    }

    @Override
    public Iterable<BookCategory> findAll() {
        return bookCategoryRepository.findAll();
    }

    @Override
    public void flush() {
        bookCategoryRepository.flush();
    }
}
