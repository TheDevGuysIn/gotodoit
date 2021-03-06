import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserExtras } from 'app/shared/model/user-extras.model';
import { getEntities } from './user-extras.reducer';

export const UserExtras = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const userExtrasList = useAppSelector(state => state.userExtras.entities);
  const loading = useAppSelector(state => state.userExtras.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="user-extras-heading" data-cy="UserExtrasHeading">
        User Extras
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/user-extras/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new User Extras
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userExtrasList && userExtrasList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Image</th>
                <th>User</th>
                <th>Organization</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userExtrasList.map((userExtras, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-extras/${userExtras.id}`} color="link" size="sm">
                      {userExtras.id}
                    </Button>
                  </td>
                  <td>
                    {userExtras.image ? (
                      <div>
                        {userExtras.imageContentType ? (
                          <a onClick={openFile(userExtras.imageContentType, userExtras.image)}>
                            <img src={`data:${userExtras.imageContentType};base64,${userExtras.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {userExtras.imageContentType}, {byteSize(userExtras.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{userExtras.user ? userExtras.user.id : ''}</td>
                  <td>
                    {userExtras.organizations
                      ? userExtras.organizations.map((val, j) => (
                          <span key={j}>
                            <Link to={`/organization/${val.id}`}>{val.name}</Link>
                            {j === userExtras.organizations.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/user-extras/${userExtras.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/user-extras/${userExtras.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/user-extras/${userExtras.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Extras found</div>
        )}
      </div>
    </div>
  );
};

export default UserExtras;
