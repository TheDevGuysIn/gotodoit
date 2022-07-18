package in.thedevguys.todo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.thedevguys.todo.IntegrationTest;
import in.thedevguys.todo.domain.UserExtras;
import in.thedevguys.todo.repository.UserExtrasRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link UserExtrasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserExtrasResourceIT {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtrasRepository userExtrasRepository;

    @Mock
    private UserExtrasRepository userExtrasRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtrasMockMvc;

    private UserExtras userExtras;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtras createEntity(EntityManager em) {
        UserExtras userExtras = new UserExtras().image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return userExtras;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtras createUpdatedEntity(EntityManager em) {
        UserExtras userExtras = new UserExtras().image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return userExtras;
    }

    @BeforeEach
    public void initTest() {
        userExtras = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtras() throws Exception {
        int databaseSizeBeforeCreate = userExtrasRepository.findAll().size();
        // Create the UserExtras
        restUserExtrasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isCreated());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testUserExtras.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createUserExtrasWithExistingId() throws Exception {
        // Create the UserExtras with an existing ID
        userExtras.setId(1L);

        int databaseSizeBeforeCreate = userExtrasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtrasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        // Get all the userExtrasList
        restUserExtrasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtras.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserExtrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(userExtrasRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtrasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userExtrasRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserExtrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userExtrasRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtrasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userExtrasRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        // Get the userExtras
        restUserExtrasMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtras.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingUserExtras() throws Exception {
        // Get the userExtras
        restUserExtrasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras
        UserExtras updatedUserExtras = userExtrasRepository.findById(userExtras.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtras are not directly saved in db
        em.detach(updatedUserExtras);
        updatedUserExtras.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserExtras.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserExtras.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtras.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtrasWithPatch() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras using partial update
        UserExtras partialUpdatedUserExtras = new UserExtras();
        partialUpdatedUserExtras.setId(userExtras.getId());

        partialUpdatedUserExtras.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserExtras.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateUserExtrasWithPatch() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras using partial update
        UserExtras partialUpdatedUserExtras = new UserExtras();
        partialUpdatedUserExtras.setId(userExtras.getId());

        partialUpdatedUserExtras.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserExtras.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeDelete = userExtrasRepository.findAll().size();

        // Delete the userExtras
        restUserExtrasMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtras.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
