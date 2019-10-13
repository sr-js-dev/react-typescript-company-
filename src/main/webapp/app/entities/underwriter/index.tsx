import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Underwriter from './underwriter';
import UnderwriterDetail from './underwriter-detail';
import UnderwriterUpdate from './underwriter-update';
import UnderwriterDeleteDialog from './underwriter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UnderwriterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UnderwriterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UnderwriterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Underwriter} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UnderwriterDeleteDialog} />
  </>
);

export default Routes;
