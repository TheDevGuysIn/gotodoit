import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './user-extras.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserExtrasDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userExtrasEntity = useAppSelector(state => state.userExtras.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userExtrasDetailsHeading">UserExtras</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userExtrasEntity.id}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {userExtrasEntity.image ? (
              <div>
                {userExtrasEntity.imageContentType ? (
                  <a onClick={openFile(userExtrasEntity.imageContentType, userExtrasEntity.image)}>
                    <img src={`data:${userExtrasEntity.imageContentType};base64,${userExtrasEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {userExtrasEntity.imageContentType}, {byteSize(userExtrasEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>User</dt>
          <dd>{userExtrasEntity.user ? userExtrasEntity.user.id : ''}</dd>
          <dt>Organization</dt>
          <dd>
            {userExtrasEntity.organizations
              ? userExtrasEntity.organizations.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {userExtrasEntity.organizations && i === userExtrasEntity.organizations.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-extras" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-extras/${userExtrasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserExtrasDetail;
