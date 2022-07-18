package in.thedevguys.todo.service;

import in.thedevguys.todo.domain.UserExtras;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UserExtras}.
 */
public interface UserExtrasService {
    /**
     * Save a userExtras.
     *
     * @param userExtras the entity to save.
     * @return the persisted entity.
     */
    UserExtras save(UserExtras userExtras);

    /**
     * Updates a userExtras.
     *
     * @param userExtras the entity to update.
     * @return the persisted entity.
     */
    UserExtras update(UserExtras userExtras);

    /**
     * Partially updates a userExtras.
     *
     * @param userExtras the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserExtras> partialUpdate(UserExtras userExtras);

    /**
     * Get all the userExtras.
     *
     * @return the list of entities.
     */
    List<UserExtras> findAll();

    /**
     * Get all the userExtras with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserExtras> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" userExtras.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExtras> findOne(Long id);

    /**
     * Delete the "id" userExtras.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
