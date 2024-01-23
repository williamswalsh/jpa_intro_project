package ie.williamswalsh.intro.repositories;

import ie.williamswalsh.intro.domain.composite.AuthorEmbedded;
import ie.williamswalsh.intro.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {}
