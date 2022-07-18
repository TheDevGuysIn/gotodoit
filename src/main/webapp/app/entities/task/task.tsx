import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITask } from 'app/shared/model/task.model';
import { getEntities } from './task.reducer';

export const Task = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taskList = useAppSelector(state => state.task.entities);
  const loading = useAppSelector(state => state.task.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="task-heading" data-cy="TaskHeading">
        Tasks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/task/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Task
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taskList && taskList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Due Date</th>
                <th>End Date</th>
                <th>Completed Date</th>
                <th>Important</th>
                <th>Deleted</th>
                <th>Project</th>
                <th>Status</th>
                <th>Owner</th>
                <th>Parent</th>
                <th>Tag</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taskList.map((task, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/task/${task.id}`} color="link" size="sm">
                      {task.id}
                    </Button>
                  </td>
                  <td>{task.title}</td>
                  <td>{task.description}</td>
                  <td>{task.dueDate ? <TextFormat type="date" value={task.dueDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{task.endDate ? <TextFormat type="date" value={task.endDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {task.completedDate ? <TextFormat type="date" value={task.completedDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{task.important ? 'true' : 'false'}</td>
                  <td>{task.deleted ? 'true' : 'false'}</td>
                  <td>{task.project ? <Link to={`/project/${task.project.id}`}>{task.project.id}</Link> : ''}</td>
                  <td>{task.status ? <Link to={`/status/${task.status.id}`}>{task.status.id}</Link> : ''}</td>
                  <td>{task.owner ? <Link to={`/user-extras/${task.owner.id}`}>{task.owner.id}</Link> : ''}</td>
                  <td>{task.parent ? <Link to={`/task/${task.parent.id}`}>{task.parent.id}</Link> : ''}</td>
                  <td>
                    {task.tags
                      ? task.tags.map((val, j) => (
                          <span key={j}>
                            <Link to={`/tag/${val.id}`}>{val.name}</Link>
                            {j === task.tags.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/task/${task.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/task/${task.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/task/${task.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Tasks found</div>
        )}
      </div>
    </div>
  );
};

export default Task;
