import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PolicyBenefit from './policy-benefit';
import PolicyBenefitDetail from './policy-benefit-detail';
import PolicyBenefitUpdate from './policy-benefit-update';
import PolicyBenefitDeleteDialog from './policy-benefit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PolicyBenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PolicyBenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PolicyBenefitDetail} />
      <ErrorBoundaryRoute path={match.url} component={PolicyBenefit} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PolicyBenefitDeleteDialog} />
  </>
);

export default Routes;
