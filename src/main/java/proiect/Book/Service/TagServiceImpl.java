package proiect.Book.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proiect.Book.Repository.TagRepository;
import proiect.Model.Book.BookTag;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    @Override
    public void save(BookTag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void deleteById(Integer ID) {
        tagRepository.deleteById(ID);
    }

    @Override
    public Optional<BookTag> findById(Integer ID) {
        return tagRepository.findById(ID);
    }

    @Override
    public Iterable<BookTag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void flush() {

    }
}
