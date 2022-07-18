package in.thedevguys.todo.service.impl;

import in.thedevguys.todo.domain.Status;
import in.thedevguys.todo.repository.StatusRepository;
import in.thedevguys.todo.service.StatusService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    @Override
    public Status update(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    @Override
    public Optional<Status> partialUpdate(Status status) {
        log.debug("Request to partially update Status : {}", status);

        return statusRepository
            .findById(status.getId())
            .map(existingStatus -> {
                if (status.getName() != null) {
                    existingStatus.setName(status.getName());
                }

                return existingStatus;
            })
            .map(statusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Status> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Status> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }
}
