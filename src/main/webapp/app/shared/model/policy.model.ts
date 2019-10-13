export const enum Status {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE'
}

export interface IPolicy {
  id?: number;
  name?: string;
  primiumPayable?: number;
  status?: Status;
  coverTypeId?: number;
  underwriterId?: number;
}

export const defaultValue: Readonly<IPolicy> = {};
