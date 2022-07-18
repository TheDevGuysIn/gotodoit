package in.thedevguys.todo.service;

import in.thedevguys.todo.domain.*; // for static metamodels
import in.thedevguys.todo.domain.UserExtras;
import in.thedevguys.todo.repository.UserExtrasRepository;
import in.thedevguys.todo.service.criteria.UserExtrasCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link UserExtras} entities in the database.
 * The main input is a {@link UserExtrasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserExtras} or a {@link Page} of {@link UserExtras} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserExtrasQueryService extends QueryService<UserExtras> {

    private final Logger log = LoggerFactory.getLogger(UserExtrasQueryService.class);

    private final UserExtrasRepository userExtrasRepository;

    public UserExtrasQueryService(UserExtrasRepository userExtrasRepository) {
        this.userExtrasRepository = userExtrasRepository;
    }

    /**
     * Return a {@link List} of {@link UserExtras} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtras> findByCriteria(UserExtrasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserExtras> specification = createSpecification(criteria);
        return userExtrasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserExtras} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExtras> findByCriteria(UserExtrasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserExtras> specification = createSpecification(criteria);
        return userExtrasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserExtrasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserExtras> specification = createSpecification(criteria);
        return userExtrasRepository.count(specification);
    }

    /**
     * Function to convert {@link UserExtrasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserExtras> createSpecification(UserExtrasCriteria criteria) {
        Specification<UserExtras> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserExtras_.id));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserExtras_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(UserExtras_.organizations, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
