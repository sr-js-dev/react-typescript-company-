import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CoverType from './cover-type';
import CoverTypeDetail from './cover-type-detail';
import CoverTypeUpdate from './cover-type-update';
import CoverTypeDeleteDialog from './cover-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoverTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoverTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoverTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CoverType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CoverTypeDeleteDialog} />
  </>
);

export default Routes;
