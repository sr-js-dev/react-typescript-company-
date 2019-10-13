import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IdType from './id-type';
import IdTypeDetail from './id-type-detail';
import IdTypeUpdate from './id-type-update';
import IdTypeDeleteDialog from './id-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={IdType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={IdTypeDeleteDialog} />
  </>
);

export default Routes;
