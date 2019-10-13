import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Company from './company';
import BenefitType from './benefit-type';
import Benefit from './benefit';
import Underwriter from './underwriter';
import ClientCategory from './client-category';
import NameTitle from './name-title';
import IdType from './id-type';
import Client from './client';
import ProductType from './product-type';
import RiskCategory from './risk-category';
import RiskClass from './risk-class';
import CoverType from './cover-type';
import Policy from './policy';
import PolicyBenefit from './policy-benefit';
import ClientPolicy from './client-policy';
import ClientPolicyPayment from './client-policy-payment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}/benefit-type`} component={BenefitType} />
      <ErrorBoundaryRoute path={`${match.url}/benefit`} component={Benefit} />
      <ErrorBoundaryRoute path={`${match.url}/underwriter`} component={Underwriter} />
      <ErrorBoundaryRoute path={`${match.url}/client-category`} component={ClientCategory} />
      <ErrorBoundaryRoute path={`${match.url}/name-title`} component={NameTitle} />
      <ErrorBoundaryRoute path={`${match.url}/id-type`} component={IdType} />
      <ErrorBoundaryRoute path={`${match.url}/client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}/product-type`} component={ProductType} />
      <ErrorBoundaryRoute path={`${match.url}/risk-category`} component={RiskCategory} />
      <ErrorBoundaryRoute path={`${match.url}/risk-class`} component={RiskClass} />
      <ErrorBoundaryRoute path={`${match.url}/cover-type`} component={CoverType} />
      <ErrorBoundaryRoute path={`${match.url}/policy`} component={Policy} />
      <ErrorBoundaryRoute path={`${match.url}/policy-benefit`} component={PolicyBenefit} />
      <ErrorBoundaryRoute path={`${match.url}/client-policy`} component={ClientPolicy} />
      <ErrorBoundaryRoute path={`${match.url}/client-policy-payment`} component={ClientPolicyPayment} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
