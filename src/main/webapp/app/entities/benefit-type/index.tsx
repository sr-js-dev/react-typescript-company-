import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BenefitType from './benefit-type';
import BenefitTypeDetail from './benefit-type-detail';
import BenefitTypeUpdate from './benefit-type-update';
import BenefitTypeDeleteDialog from './benefit-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BenefitTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BenefitTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BenefitTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={BenefitType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BenefitTypeDeleteDialog} />
  </>
);

export default Routes;
