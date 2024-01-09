package proiect.Model.Book.DTO;

import jakarta.validation.constraints.NotNull;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookTag;

public class BookTagDTO {
    @NotNull
    private Book book;
    private BookTag tag;
    public BookTagDTO(){}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookTag getTag() {
        return tag;
    }

    public void setTag(BookTag tag) {
        this.tag = tag;
    }
}
