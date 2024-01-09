package proiect.Model.Book;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import proiect.Model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Entity
public class Book extends AbstractEntity {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public List<BookTag> getTags() {
        return tags;
    }

    public void addTag(BookTag tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return title;
    }

}
