import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Benefit from './benefit';
import BenefitDetail from './benefit-detail';
import BenefitUpdate from './benefit-update';
import BenefitDeleteDialog from './benefit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BenefitDetail} />
      <ErrorBoundaryRoute path={match.url} component={Benefit} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BenefitDeleteDialog} />
  </>
);

export default Routes;
