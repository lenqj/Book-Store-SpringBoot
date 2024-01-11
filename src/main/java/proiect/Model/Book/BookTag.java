package proiect.Model.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
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
public class BookTag extends AbstractEntity {
    @Size(min = 1, max = 25)
    @NotBlank
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Book> books = new ArrayList<>();

    public BookTag(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public BookTag() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getDisplayName() {
        return "#" + name + " ";
    }
}
