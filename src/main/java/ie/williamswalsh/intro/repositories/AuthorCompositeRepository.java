package ie.williamswalsh.intro.repositories;

import ie.williamswalsh.intro.domain.composite.AuthorComposite;
import ie.williamswalsh.intro.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {}
