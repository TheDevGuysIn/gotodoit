package in.thedevguys.todo.repository;

import in.thedevguys.todo.domain.UserExtras;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserExtrasRepositoryWithBagRelationshipsImpl implements UserExtrasRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserExtras> fetchBagRelationships(Optional<UserExtras> userExtras) {
        return userExtras.map(this::fetchOrganizations);
    }

    @Override
    public Page<UserExtras> fetchBagRelationships(Page<UserExtras> userExtras) {
        return new PageImpl<>(fetchBagRelationships(userExtras.getContent()), userExtras.getPageable(), userExtras.getTotalElements());
    }

    @Override
    public List<UserExtras> fetchBagRelationships(List<UserExtras> userExtras) {
        return Optional.of(userExtras).map(this::fetchOrganizations).orElse(Collections.emptyList());
    }

    UserExtras fetchOrganizations(UserExtras result) {
        return entityManager
            .createQuery(
                "select userExtras from UserExtras userExtras left join fetch userExtras.organizations where userExtras is :userExtras",
                UserExtras.class
            )
            .setParameter("userExtras", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserExtras> fetchOrganizations(List<UserExtras> userExtras) {
        return entityManager
            .createQuery(
                "select distinct userExtras from UserExtras userExtras left join fetch userExtras.organizations where userExtras in :userExtras",
                UserExtras.class
            )
            .setParameter("userExtras", userExtras)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
