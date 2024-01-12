package ie.williamswalsh.intro;

import ie.williamswalsh.intro.domain.Book;
import ie.williamswalsh.intro.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Brings up hibernate, h2-db & spring data JPA.
@DataJpaTest
// One way to initialize data with a command line runner class for each test.
// Will run every run method in each class in the basePackage:
@ComponentScan(basePackages = {"ie.williamswalsh.intro.bootstrap"})
public class SpringBootJpaTestSliceWithDataInitializer {

    @Autowired
    BookRepository bookRepository;

//    Define an explicit test order:
    @Order(1)
//    Commit the transaction in this transactional context(Test).
//    Will affect the data in the db(h2 - temporary). Book count will be 1 for second test.
    @Commit
    @Test
    void testJpaSlice() {
        long countBefore = bookRepository.count();
        bookRepository.save(new Book("Chat GPT", "7867544", "John Doe"));
        long countAfter = bookRepository.count();

        // Command line runner doesn't execute with @DataJpaTest - does execute for @SpringBootTest
        assertThat(countBefore).isEqualTo(2);
        assertThat(countAfter).isEqualTo(3);
    }

    @Order(2)
    @Test
    void testJpaSliceWithTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);
    }
}
