package proiect.Data.Repository.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiect.Model.Book.BookCategory;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Integer> {
}