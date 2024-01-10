package proiect.Model.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import proiect.Model.AbstractEntity;

import java.time.LocalDate;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Getter
@Entity
public class BookDetails extends AbstractEntity {
    @NotBlank(message = "Author is required.")
    private String author;
    @Size(max = 500, message = "Description too long.")
    private String description;
    private LocalDate publishedDate;
    @PositiveOrZero(message = "Stock must be greater or equal to 0.")
    private Long stock;
    @PositiveOrZero(message = "Price must be greater or equal to 0.")
    private Long price;

    @Lob
    @Column(length = 999999999)
    private byte[] imageData;
    @Transient
    private MultipartFile imageFile;

    public BookDetails(String author, String description, LocalDate publishedDate, Long stock, Long price, byte[] imageData) {
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
        this.stock = stock;
        this.price = price;
        this.imageData = imageData;
    }

    public BookDetails() {
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getBase64imageData() {
        return encodeBase64String(imageData);
    }
}
