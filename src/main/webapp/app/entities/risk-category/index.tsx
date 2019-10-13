import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RiskCategory from './risk-category';
import RiskCategoryDetail from './risk-category-detail';
import RiskCategoryUpdate from './risk-category-update';
import RiskCategoryDeleteDialog from './risk-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RiskCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RiskCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RiskCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={RiskCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RiskCategoryDeleteDialog} />
  </>
);

export default Routes;
