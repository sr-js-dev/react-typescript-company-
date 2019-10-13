export const enum UserType {
  COMPANY = 'COMPANY',
  INDIVIDUAL = 'INDIVIDUAL'
}

export interface ICompany {
  id?: number;
  usertype?: UserType;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  name?: string;
  email?: string;
  displayName?: string;
  logo?: string;
  telephone?: string;
  contactPersion?: string;
  mobile?: string;
  address?: string;
  streetAddress?: string;
  county?: string;
  country?: string;
  pinNumber?: number;
}

export const defaultValue: Readonly<ICompany> = {};
