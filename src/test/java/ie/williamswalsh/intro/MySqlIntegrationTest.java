package ie.williamswalsh.intro;

import ie.williamswalsh.intro.domain.BookNatural;
import ie.williamswalsh.intro.domain.composite.AuthorComposite;
import ie.williamswalsh.intro.domain.composite.NameId;
import ie.williamswalsh.intro.repositories.AuthorCompositeRepository;
import ie.williamswalsh.intro.repositories.BookNaturalRepository;
import ie.williamswalsh.intro.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local") // enable the "local" profile config application-local.properties - mysql db
@DataJpaTest // this causes the h2 datasource to be created
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Don't replace the test datasource.
@ComponentScan(basePackages = {"ie.williamswalsh.intro.bootstrap"})  // will execute run() method - adding 2 books.
public class MySqlIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Test
    void testMySql() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }

    @Test
    void bookNaturalSaveTest() {
        BookNatural book = new BookNatural();
        book.setTitle("To kill a mockingbird");

        bookNaturalRepository.save(book);
        BookNatural fetched = bookNaturalRepository.getReferenceById(book.getTitle());

        assertThat(fetched).isNotNull();
    }

    @Test
    void authorCompositeTest() {
        AuthorComposite author = new AuthorComposite("Dan", "Brown", "America");
        AuthorComposite saved = authorCompositeRepository.save(author);
        assertThat(saved).isNotNull();

        NameId id = new NameId(author.getFirstName(), author.getLastName());
        AuthorComposite fetched = authorCompositeRepository.getReferenceById(id);
        assertThat(fetched).isNotNull();
    }
}
