export interface ICoverType {
  id?: number;
  name?: string;
  brokerCommission?: number;
  description?: string;
  riskClassId?: number;
}

export const defaultValue: Readonly<ICoverType> = {};
