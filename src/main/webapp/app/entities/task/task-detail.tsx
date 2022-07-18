import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './task.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">Task</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{taskEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>{taskEntity.dueDate ? <TextFormat value={taskEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{taskEntity.endDate ? <TextFormat value={taskEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="completedDate">Completed Date</span>
          </dt>
          <dd>
            {taskEntity.completedDate ? <TextFormat value={taskEntity.completedDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="important">Important</span>
          </dt>
          <dd>{taskEntity.important ? 'true' : 'false'}</dd>
          <dt>
            <span id="deleted">Deleted</span>
          </dt>
          <dd>{taskEntity.deleted ? 'true' : 'false'}</dd>
          <dt>Project</dt>
          <dd>{taskEntity.project ? taskEntity.project.id : ''}</dd>
          <dt>Status</dt>
          <dd>{taskEntity.status ? taskEntity.status.id : ''}</dd>
          <dt>Owner</dt>
          <dd>{taskEntity.owner ? taskEntity.owner.id : ''}</dd>
          <dt>Parent</dt>
          <dd>{taskEntity.parent ? taskEntity.parent.id : ''}</dd>
          <dt>Tag</dt>
          <dd>
            {taskEntity.tags
              ? taskEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {taskEntity.tags && i === taskEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
