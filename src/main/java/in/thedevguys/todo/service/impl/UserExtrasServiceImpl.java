package in.thedevguys.todo.service.impl;

import in.thedevguys.todo.domain.UserExtras;
import in.thedevguys.todo.repository.UserExtrasRepository;
import in.thedevguys.todo.service.UserExtrasService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserExtras}.
 */
@Service
@Transactional
public class UserExtrasServiceImpl implements UserExtrasService {

    private final Logger log = LoggerFactory.getLogger(UserExtrasServiceImpl.class);

    private final UserExtrasRepository userExtrasRepository;

    public UserExtrasServiceImpl(UserExtrasRepository userExtrasRepository) {
        this.userExtrasRepository = userExtrasRepository;
    }

    @Override
    public UserExtras save(UserExtras userExtras) {
        log.debug("Request to save UserExtras : {}", userExtras);
        return userExtrasRepository.save(userExtras);
    }

    @Override
    public UserExtras update(UserExtras userExtras) {
        log.debug("Request to save UserExtras : {}", userExtras);
        return userExtrasRepository.save(userExtras);
    }

    @Override
    public Optional<UserExtras> partialUpdate(UserExtras userExtras) {
        log.debug("Request to partially update UserExtras : {}", userExtras);

        return userExtrasRepository
            .findById(userExtras.getId())
            .map(existingUserExtras -> {
                if (userExtras.getImage() != null) {
                    existingUserExtras.setImage(userExtras.getImage());
                }
                if (userExtras.getImageContentType() != null) {
                    existingUserExtras.setImageContentType(userExtras.getImageContentType());
                }

                return existingUserExtras;
            })
            .map(userExtrasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserExtras> findAll() {
        log.debug("Request to get all UserExtras");
        return userExtrasRepository.findAllWithEagerRelationships();
    }

    public Page<UserExtras> findAllWithEagerRelationships(Pageable pageable) {
        return userExtrasRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserExtras> findOne(Long id) {
        log.debug("Request to get UserExtras : {}", id);
        return userExtrasRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtras : {}", id);
        userExtrasRepository.deleteById(id);
    }
}
