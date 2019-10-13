export interface IRiskCategory {
  id?: number;
  name?: string;
  description?: string;
  productTypeId?: number;
}

export const defaultValue: Readonly<IRiskCategory> = {};
