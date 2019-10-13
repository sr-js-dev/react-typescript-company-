export const enum BenefitRate {
  FIXED = 'FIXED',
  PERCENTAGE = 'PERCENTAGE'
}

export interface IPolicyBenefit {
  id?: number;
  benefitRate?: BenefitRate;
  benefitValue?: string;
  benefitMinValue?: number;
  benefitId?: number;
  policyId?: number;
}

export const defaultValue: Readonly<IPolicyBenefit> = {};
