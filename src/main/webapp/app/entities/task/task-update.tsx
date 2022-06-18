import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IStatus } from 'app/shared/model/status.model';
import { getEntities as getStatuses } from 'app/entities/status/status.reducer';
import { IUserExtras } from 'app/shared/model/user-extras.model';
import { getEntities as getUserExtras } from 'app/entities/user-extras/user-extras.reducer';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { ITask } from 'app/shared/model/task.model';
import { getEntity, updateEntity, createEntity, reset } from './task.reducer';

export const TaskUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const projects = useAppSelector(state => state.project.entities);
  const statuses = useAppSelector(state => state.status.entities);
  const userExtras = useAppSelector(state => state.userExtras.entities);
  const tasks = useAppSelector(state => state.task.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const taskEntity = useAppSelector(state => state.task.entity);
  const loading = useAppSelector(state => state.task.loading);
  const updating = useAppSelector(state => state.task.updating);
  const updateSuccess = useAppSelector(state => state.task.updateSuccess);
  const handleClose = () => {
    props.history.push('/task');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProjects({}));
    dispatch(getStatuses({}));
    dispatch(getUserExtras({}));
    dispatch(getTasks({}));
    dispatch(getTags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taskEntity,
      ...values,
      tags: mapIdList(values.tags),
      project: projects.find(it => it.id.toString() === values.project.toString()),
      status: statuses.find(it => it.id.toString() === values.status.toString()),
      owner: userExtras.find(it => it.id.toString() === values.owner.toString()),
      parent: tasks.find(it => it.id.toString() === values.parent.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...taskEntity,
          project: taskEntity?.project?.id,
          status: taskEntity?.status?.id,
          owner: taskEntity?.owner?.id,
          parent: taskEntity?.parent?.id,
          tags: taskEntity?.tags?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="goToDoItApp.task.home.createOrEditLabel" data-cy="TaskCreateUpdateHeading">
            Create or edit a Task
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="task-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Title"
                id="task-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 3, message: 'This field is required to be at least 3 characters.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Description" id="task-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Due Date" id="task-dueDate" name="dueDate" data-cy="dueDate" type="date" />
              <ValidatedField label="End Date" id="task-endDate" name="endDate" data-cy="endDate" type="date" />
              <ValidatedField label="Completed Date" id="task-completedDate" name="completedDate" data-cy="completedDate" type="date" />
              <ValidatedField label="Important" id="task-important" name="important" data-cy="important" check type="checkbox" />
              <ValidatedField label="Deleted" id="task-deleted" name="deleted" data-cy="deleted" check type="checkbox" />
              <ValidatedField id="task-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="task-status" name="status" data-cy="status" label="Status" type="select">
                <option value="" key="0" />
                {statuses
                  ? statuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="task-owner" name="owner" data-cy="owner" label="Owner" type="select">
                <option value="" key="0" />
                {userExtras
                  ? userExtras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="task-parent" name="parent" data-cy="parent" label="Parent" type="select">
                <option value="" key="0" />
                {tasks
                  ? tasks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Tag" id="task-tag" data-cy="tag" type="select" multiple name="tags">
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TaskUpdate;
