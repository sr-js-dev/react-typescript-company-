export const enum Status {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE'
}

export interface IClient {
  id?: number;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  clientName?: string;
  ledgerName?: string;
  clientPrintName?: string;
  idNumber?: string;
  contactPersion?: string;
  telephone?: string;
  mobile?: string;
  email?: string;
  address?: string;
  streetAddress?: string;
  county?: string;
  country?: string;
  pinNumber?: number;
  notes?: string;
  status?: Status;
  categoryId?: number;
  titleId?: number;
  idTypeId?: number;
}

export const defaultValue: Readonly<IClient> = {};
