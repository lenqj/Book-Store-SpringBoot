package proiect.Model.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import proiect.Model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BookCategory extends AbstractEntity {
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;
    @OneToMany(mappedBy = "bookCategory")
    private final List<Book> books = new ArrayList<>();
    public BookCategory(@Size(min = 3, message = "Name must be at least 3 characters long.") String name) {
        this.name = name;
    }
    public BookCategory() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Book> getBooks() {
        return books;
    }
    @Override
    public String toString() {
        return name;
    }
}
