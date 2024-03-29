package ie.williamswalsh.intro;

import ie.williamswalsh.intro.domain.Book;
import ie.williamswalsh.intro.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("default")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Brings up hibernate, h2-db & spring data JPA.
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Don't create any h2 datasource.
public class SpringBootJpaTestSlice {

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
        bookRepository.save(new Book("Chat GPT", "7867544", "John Doe", null));
        long countAfter = bookRepository.count();

        // Command line runner doesn't execute with @DataJpaTest - does execute for @SpringBootTest
        assertThat(countBefore).isEqualTo(0);
        assertThat(countAfter).isEqualTo(1);
    }

    @Order(2)
    @Test
    void testJpaSliceWithTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(1);
    }
}
