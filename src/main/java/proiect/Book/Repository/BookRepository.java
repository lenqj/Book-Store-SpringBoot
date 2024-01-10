package proiect.Book.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiect.Model.Book.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
