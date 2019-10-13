import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Policy from './policy';
import PolicyDetail from './policy-detail';
import PolicyUpdate from './policy-update';
import PolicyDeleteDialog from './policy-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PolicyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PolicyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PolicyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Policy} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PolicyDeleteDialog} />
  </>
);

export default Routes;
