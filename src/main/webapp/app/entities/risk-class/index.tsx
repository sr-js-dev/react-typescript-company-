import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RiskClass from './risk-class';
import RiskClassDetail from './risk-class-detail';
import RiskClassUpdate from './risk-class-update';
import RiskClassDeleteDialog from './risk-class-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RiskClassUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RiskClassUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RiskClassDetail} />
      <ErrorBoundaryRoute path={match.url} component={RiskClass} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RiskClassDeleteDialog} />
  </>
);

export default Routes;
