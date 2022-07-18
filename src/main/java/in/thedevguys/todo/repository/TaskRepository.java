package in.thedevguys.todo.repository;

import in.thedevguys.todo.domain.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(
        value = "select distinct task from Task task left join fetch task.tags",
        countQuery = "select count(distinct task) from Task task"
    )
    Page<Task> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct task from Task task left join fetch task.tags")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.tags where task.id =:id")
    Optional<Task> findOneWithEagerRelationships(@Param("id") Long id);
}
