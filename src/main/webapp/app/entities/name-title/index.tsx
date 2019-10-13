import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NameTitle from './name-title';
import NameTitleDetail from './name-title-detail';
import NameTitleUpdate from './name-title-update';
import NameTitleDeleteDialog from './name-title-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NameTitleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NameTitleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NameTitleDetail} />
      <ErrorBoundaryRoute path={match.url} component={NameTitle} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NameTitleDeleteDialog} />
  </>
);

export default Routes;
