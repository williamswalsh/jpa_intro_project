package ie.williamswalsh.intro.bootstrap;

import ie.williamswalsh.intro.domain.Book;
import ie.williamswalsh.intro.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring starts the command line runner when the application is started.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");
        Book bookSIA = new Book("Spring In action", "234234", "Oreilly");

        System.out.println("SIA ID before saving: " + bookSIA.getId());

//        The "saved object ID" will have a value.
        Book savedDDD = bookRepository.save(bookDDD);
        Book savedSIA = bookRepository.save(bookSIA);

        System.out.println("SIA ID after saving: " + bookSIA.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.print("ID: " + book.getId());
            System.out.println(", Title: " + book.getTitle());
        });
    }
}
