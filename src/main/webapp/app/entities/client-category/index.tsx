import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientCategory from './client-category';
import ClientCategoryDetail from './client-category-detail';
import ClientCategoryUpdate from './client-category-update';
import ClientCategoryDeleteDialog from './client-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientCategoryDeleteDialog} />
  </>
);

export default Routes;
