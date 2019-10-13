import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientPolicy from './client-policy';
import ClientPolicyDetail from './client-policy-detail';
import ClientPolicyUpdate from './client-policy-update';
import ClientPolicyDeleteDialog from './client-policy-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientPolicyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientPolicyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientPolicyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientPolicy} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientPolicyDeleteDialog} />
  </>
);

export default Routes;
