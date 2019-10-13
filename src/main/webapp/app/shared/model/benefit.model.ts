export const enum BenefitRate {
  FIXED = 'FIXED',
  PERCENTAGE = 'PERCENTAGE'
}

export interface IBenefit {
  id?: number;
  name?: string;
  benefitRate?: BenefitRate;
  description?: string;
  benefitTypeId?: number;
}

export const defaultValue: Readonly<IBenefit> = {};
