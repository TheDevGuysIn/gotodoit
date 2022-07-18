import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserExtras from './user-extras';
import UserExtrasDetail from './user-extras-detail';
import UserExtrasUpdate from './user-extras-update';
import UserExtrasDeleteDialog from './user-extras-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserExtrasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserExtrasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserExtrasDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserExtras} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserExtrasDeleteDialog} />
  </>
);

export default Routes;
