package ie.williamswalsh.intro.repositories;

import ie.williamswalsh.intro.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {}
