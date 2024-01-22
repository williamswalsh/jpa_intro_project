package ie.williamswalsh.intro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;


// Object that uses a natural key as an Identity.
// Not recommended.
// Should use an artificial key instead -> called a surrogate key.
@Entity
public class BookNatural {

    @Id
    private String title;
    private String isbn;
    private String publisher;

    public BookNatural() {}

    public BookNatural(String title, String isbn, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookNatural book = (BookNatural) o;

        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
