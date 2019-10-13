import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientPolicyPayment from './client-policy-payment';
import ClientPolicyPaymentDetail from './client-policy-payment-detail';
import ClientPolicyPaymentUpdate from './client-policy-payment-update';
import ClientPolicyPaymentDeleteDialog from './client-policy-payment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientPolicyPaymentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientPolicyPaymentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientPolicyPaymentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientPolicyPayment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientPolicyPaymentDeleteDialog} />
  </>
);

export default Routes;
