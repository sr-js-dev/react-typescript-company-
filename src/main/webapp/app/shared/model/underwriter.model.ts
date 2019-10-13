export const enum Status {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE'
}

export interface IUnderwriter {
  id?: number;
  name?: string;
  description?: string;
  logo?: string;
  binder?: string;
  website?: string;
  contactPersion?: string;
  telephone?: string;
  mobile?: string;
  email?: string;
  address?: string;
  streetAddress?: string;
  county?: string;
  country?: string;
  pinNumber?: number;
  status?: Status;
}

export const defaultValue: Readonly<IUnderwriter> = {};
