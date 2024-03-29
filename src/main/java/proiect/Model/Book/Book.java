package proiect.Model.Book;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import proiect.Model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@DynamicUpdate
public class Book extends AbstractEntity{
    @NotBlank(message = "Title is required.")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters.")
    private String title;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private BookDetails bookDetails;
    @ManyToOne
    @NotNull(message = "Category is required.")
    private BookCategory bookCategory;
    @ManyToMany
    private final List<BookTag> tags = new ArrayList<>();

    public Book(String title, BookCategory bookCategory, BookDetails bookDetails) {
        this.title = title;
        this.bookCategory = bookCategory;
        this.bookDetails = bookDetails;
    }

    public Book() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public void addTag(BookTag tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return title;
    }

}
