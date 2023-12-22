package ie.williamswalsh.intro.repositories;

import ie.williamswalsh.intro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data will pick up this repository as its extended the JPA repo interface.
 */
public interface BookRepository extends JpaRepository<Book, Long> {}
