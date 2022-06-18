package in.thedevguys.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "tasks" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "organizations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "organizations" }, allowSetters = true)
    private Set<UserExtras> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Organization image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Organization imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setOrganization(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setOrganization(this));
        }
        this.projects = projects;
    }

    public Organization projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Organization addProject(Project project) {
        this.projects.add(project);
        project.setOrganization(this);
        return this;
    }

    public Organization removeProject(Project project) {
        this.projects.remove(project);
        project.setOrganization(null);
        return this;
    }

    public Set<UserExtras> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserExtras> userExtras) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeOrganization(this));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.addOrganization(this));
        }
        this.users = userExtras;
    }

    public Organization users(Set<UserExtras> userExtras) {
        this.setUsers(userExtras);
        return this;
    }

    public Organization addUser(UserExtras userExtras) {
        this.users.add(userExtras);
        userExtras.getOrganizations().add(this);
        return this;
    }

    public Organization removeUser(UserExtras userExtras) {
        this.users.remove(userExtras);
        userExtras.getOrganizations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
