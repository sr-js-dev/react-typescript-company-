import { Moment } from 'moment';

export const enum PaymentStatus {
  UNPAID = 'UNPAID',
  PARTIAL = 'PARTIAL',
  PAID = 'PAID'
}

export interface IClientPolicy {
  id?: number;
  policyDate?: Moment;
  invoiceNo?: string;
  startDate?: Moment;
  endDate?: Moment;
  primiumPayable?: number;
  openPayable?: number;
  paymentStatus?: PaymentStatus;
  clientId?: number;
  policyId?: number;
}

export const defaultValue: Readonly<IClientPolicy> = {};
