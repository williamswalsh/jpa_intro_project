package ie.williamswalsh.intro.bootstrap;

import ie.williamswalsh.intro.domain.AuthorUuid;
import ie.williamswalsh.intro.domain.Book;
import ie.williamswalsh.intro.domain.BookNatural;
import ie.williamswalsh.intro.domain.BookUuid;
import ie.williamswalsh.intro.repositories.AuthorUuidRepository;
import ie.williamswalsh.intro.repositories.BookNaturalRepository;
import ie.williamswalsh.intro.repositories.BookRepository;
import ie.williamswalsh.intro.repositories.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Spring starts the command line runner after the application is started.
 */
@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    private final AuthorUuidRepository authorUuidRepository;

    private final BookUuidRepository bookUuidRepository;

    private final BookNaturalRepository bookNaturalRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository, BookNaturalRepository bookNaturalRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
        this.bookNaturalRepository = bookNaturalRepository;
    }

    @Override
    public void run(String... args) {
        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        Book bookSIA = new Book("Spring In action", "234234", "Oreilly", null);

        System.out.println("SIA ID before saving: " + bookSIA.getId());

//        The "saved object ID" will have a value.
        bookRepository.save(bookDDD);
        bookRepository.save(bookSIA);

        System.out.println("SIA ID after saving: " + bookSIA.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.print("ID: " + book.getId());
            System.out.println(", Title: " + book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("John");
        authorUuid.setLastName("Doe");

        AuthorUuid savedAuthorUuid = authorUuidRepository.save(authorUuid);
        System.out.println("Author Saved UUID: " + savedAuthorUuid.getId());

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("RFC compliant UUID");
        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Book saved UUID: " + savedBookUuid.getId());

        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("The Hobbit");

        bookNaturalRepository.save(bookNatural);
    }
}
