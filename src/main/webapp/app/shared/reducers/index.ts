import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import benefitType, {
  BenefitTypeState
} from 'app/entities/benefit-type/benefit-type.reducer';
// prettier-ignore
import benefit, {
  BenefitState
} from 'app/entities/benefit/benefit.reducer';
// prettier-ignore
import underwriter, {
  UnderwriterState
} from 'app/entities/underwriter/underwriter.reducer';
// prettier-ignore
import clientCategory, {
  ClientCategoryState
} from 'app/entities/client-category/client-category.reducer';
// prettier-ignore
import nameTitle, {
  NameTitleState
} from 'app/entities/name-title/name-title.reducer';
// prettier-ignore
import idType, {
  IdTypeState
} from 'app/entities/id-type/id-type.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
// prettier-ignore
import productType, {
  ProductTypeState
} from 'app/entities/product-type/product-type.reducer';
// prettier-ignore
import riskCategory, {
  RiskCategoryState
} from 'app/entities/risk-category/risk-category.reducer';
// prettier-ignore
import riskClass, {
  RiskClassState
} from 'app/entities/risk-class/risk-class.reducer';
// prettier-ignore
import coverType, {
  CoverTypeState
} from 'app/entities/cover-type/cover-type.reducer';
// prettier-ignore
import policy, {
  PolicyState
} from 'app/entities/policy/policy.reducer';
// prettier-ignore
import policyBenefit, {
  PolicyBenefitState
} from 'app/entities/policy-benefit/policy-benefit.reducer';
// prettier-ignore
import clientPolicy, {
  ClientPolicyState
} from 'app/entities/client-policy/client-policy.reducer';
// prettier-ignore
import clientPolicyPayment, {
  ClientPolicyPaymentState
} from 'app/entities/client-policy-payment/client-policy-payment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly company: CompanyState;
  readonly benefitType: BenefitTypeState;
  readonly benefit: BenefitState;
  readonly underwriter: UnderwriterState;
  readonly clientCategory: ClientCategoryState;
  readonly nameTitle: NameTitleState;
  readonly idType: IdTypeState;
  readonly client: ClientState;
  readonly productType: ProductTypeState;
  readonly riskCategory: RiskCategoryState;
  readonly riskClass: RiskClassState;
  readonly coverType: CoverTypeState;
  readonly policy: PolicyState;
  readonly policyBenefit: PolicyBenefitState;
  readonly clientPolicy: ClientPolicyState;
  readonly clientPolicyPayment: ClientPolicyPaymentState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  company,
  benefitType,
  benefit,
  underwriter,
  clientCategory,
  nameTitle,
  idType,
  client,
  productType,
  riskCategory,
  riskClass,
  coverType,
  policy,
  policyBenefit,
  clientPolicy,
  clientPolicyPayment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
