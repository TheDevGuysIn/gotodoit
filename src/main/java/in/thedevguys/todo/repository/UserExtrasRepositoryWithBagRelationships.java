package in.thedevguys.todo.repository;

import in.thedevguys.todo.domain.UserExtras;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserExtrasRepositoryWithBagRelationships {
    Optional<UserExtras> fetchBagRelationships(Optional<UserExtras> userExtras);

    List<UserExtras> fetchBagRelationships(List<UserExtras> userExtras);

    Page<UserExtras> fetchBagRelationships(Page<UserExtras> userExtras);
}
