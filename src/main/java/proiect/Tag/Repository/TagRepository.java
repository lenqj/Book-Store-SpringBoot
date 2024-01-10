package proiect.Tag.Repository;

import org.springframework.data.repository.CrudRepository;
import proiect.Model.Book.BookTag;

public interface TagRepository extends CrudRepository<BookTag, Integer> {
}
