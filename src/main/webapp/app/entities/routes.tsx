import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Organization from './organization';
import Project from './project';
import Task from './task';
import Tag from './tag';
import Status from './status';
import UserExtras from './user-extras';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}organization`} component={Organization} />
        <ErrorBoundaryRoute path={`${match.url}project`} component={Project} />
        <ErrorBoundaryRoute path={`${match.url}task`} component={Task} />
        <ErrorBoundaryRoute path={`${match.url}tag`} component={Tag} />
        <ErrorBoundaryRoute path={`${match.url}status`} component={Status} />
        <ErrorBoundaryRoute path={`${match.url}user-extras`} component={UserExtras} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
