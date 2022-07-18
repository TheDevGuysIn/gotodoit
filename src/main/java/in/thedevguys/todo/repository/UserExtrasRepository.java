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
public interface UserExtrasRepository
    extends UserExtrasRepositoryWithBagRelationships, JpaRepository<UserExtras, Long>, JpaSpecificationExecutor<UserExtras> {
    default Optional<UserExtras> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<UserExtras> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<UserExtras> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
