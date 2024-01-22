package ie.williamswalsh.intro;

import ie.williamswalsh.intro.domain.AuthorUuid;
import ie.williamswalsh.intro.repositories.AuthorUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local") // enable the "local" profile config application-local.properties - mysql db
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorUuidTest {

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Test
    void initialCountTest() {
        long countBefore = authorUuidRepository.count();
        assertThat(countBefore).isEqualTo(1);
    }

    @Test
    void countTest() {
        long countBefore = authorUuidRepository.count();
        AuthorUuid author = new AuthorUuid("Daniel", "Kahneman");
        authorUuidRepository.save(author);
        long countAfter = authorUuidRepository.count();

        assertThat(countBefore).isEqualTo(1);
        assertThat(countAfter).isEqualTo(2);
    }

    @Test
    void displayCountRollbackAcrossTestsTest() {
        long countBefore = authorUuidRepository.count();
        AuthorUuid author = new AuthorUuid("Lewis", "Carroll");
        authorUuidRepository.save(author);
        long countAfter = authorUuidRepository.count();

        assertThat(countBefore).isEqualTo(1);
        assertThat(countAfter).isEqualTo(2);
//         Still 1 & 2 each test changes are rolled back from db.
    }

    @Test
    void checkIdTest() {
        AuthorUuid author = new AuthorUuid("Lewis", "Carroll");
        AuthorUuid savedAuthor = authorUuidRepository.save(author);
        AuthorUuid fetchedAuthor = authorUuidRepository.getReferenceById(savedAuthor.getId());
        
        assertThat(fetchedAuthor).isNotNull();
        assertThat(fetchedAuthor.getId()).isNotNull();
        assertThat(fetchedAuthor.getId()).isInstanceOf(UUID.class);

    }
}
