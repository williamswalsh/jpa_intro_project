package ie.williamswalsh.intro;

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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Don't create any h2 datasource.
@ComponentScan(basePackages = {"ie.williamswalsh.intro.bootstrap"})  // will execute run() method - adding 2 books.
public class MySqlIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testMySql() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
