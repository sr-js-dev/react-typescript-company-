export interface IRiskClass {
  id?: number;
  name?: string;
  description?: string;
  riskCategoryId?: number;
}

export const defaultValue: Readonly<IRiskClass> = {};
