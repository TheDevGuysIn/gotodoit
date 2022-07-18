package in.thedevguys.todo.service;

import in.thedevguys.todo.domain.Status;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Status}.
 */
public interface StatusService {
    /**
     * Save a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    Status save(Status status);

    /**
     * Updates a status.
     *
     * @param status the entity to update.
     * @return the persisted entity.
     */
    Status update(Status status);

    /**
     * Partially updates a status.
     *
     * @param status the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Status> partialUpdate(Status status);

    /**
     * Get all the statuses.
     *
     * @return the list of entities.
     */
    List<Status> findAll();

    /**
     * Get the "id" status.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Status> findOne(Long id);

    /**
     * Delete the "id" status.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
