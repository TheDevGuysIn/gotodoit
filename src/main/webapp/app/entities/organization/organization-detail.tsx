import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './organization.reducer';

export const OrganizationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const organizationEntity = useAppSelector(state => state.organization.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organizationDetailsHeading">Organization</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{organizationEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{organizationEntity.name}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {organizationEntity.image ? (
              <div>
                {organizationEntity.imageContentType ? (
                  <a onClick={openFile(organizationEntity.imageContentType, organizationEntity.image)}>
                    <img
                      src={`data:${organizationEntity.imageContentType};base64,${organizationEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {organizationEntity.imageContentType}, {byteSize(organizationEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/organization" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organization/${organizationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrganizationDetail;
