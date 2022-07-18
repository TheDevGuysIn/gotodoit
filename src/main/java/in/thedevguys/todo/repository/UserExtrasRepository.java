package in.thedevguys.todo.repository;

import in.thedevguys.todo.domain.UserExtras;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserExtras entity.
 */
@Repository
public interface UserExtrasRepository extends JpaRepository<UserExtras, Long> {
    @Query(
        value = "select distinct userExtras from UserExtras userExtras left join fetch userExtras.organizations",
        countQuery = "select count(distinct userExtras) from UserExtras userExtras"
    )
    Page<UserExtras> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userExtras from UserExtras userExtras left join fetch userExtras.organizations")
    List<UserExtras> findAllWithEagerRelationships();

    @Query("select userExtras from UserExtras userExtras left join fetch userExtras.organizations where userExtras.id =:id")
    Optional<UserExtras> findOneWithEagerRelationships(@Param("id") Long id);
}
