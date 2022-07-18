package in.thedevguys.todo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link in.thedevguys.todo.domain.Task} entity. This class is used
 * in {@link in.thedevguys.todo.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TaskCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private LocalDateFilter dueDate;

    private LocalDateFilter endDate;

    private LocalDateFilter completedDate;

    private BooleanFilter important;

    private BooleanFilter deleted;

    private LongFilter projectId;

    private LongFilter statusId;

    private LongFilter ownerId;

    private LongFilter parentId;

    private LongFilter tagId;

    private Boolean distinct;

    public TaskCriteria() {}

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.completedDate = other.completedDate == null ? null : other.completedDate.copy();
        this.important = other.important == null ? null : other.important.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.tagId = other.tagId == null ? null : other.tagId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public LocalDateFilter getCompletedDate() {
        return completedDate;
    }

    public LocalDateFilter completedDate() {
        if (completedDate == null) {
            completedDate = new LocalDateFilter();
        }
        return completedDate;
    }

    public void setCompletedDate(LocalDateFilter completedDate) {
        this.completedDate = completedDate;
    }

    public BooleanFilter getImportant() {
        return important;
    }

    public BooleanFilter important() {
        if (important == null) {
            important = new BooleanFilter();
        }
        return important;
    }

    public void setImportant(BooleanFilter important) {
        this.important = important;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            deleted = new BooleanFilter();
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public LongFilter ownerId() {
        if (ownerId == null) {
            ownerId = new LongFilter();
        }
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getTagId() {
        return tagId;
    }

    public LongFilter tagId() {
        if (tagId == null) {
            tagId = new LongFilter();
        }
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(completedDate, that.completedDate) &&
            Objects.equals(important, that.important) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(tagId, that.tagId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            description,
            dueDate,
            endDate,
            completedDate,
            important,
            deleted,
            projectId,
            statusId,
            ownerId,
            parentId,
            tagId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (completedDate != null ? "completedDate=" + completedDate + ", " : "") +
            (important != null ? "important=" + important + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            (tagId != null ? "tagId=" + tagId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
