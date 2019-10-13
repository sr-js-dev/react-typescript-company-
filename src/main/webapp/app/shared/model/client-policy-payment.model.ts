import { Moment } from 'moment';

export const enum PaymentMethod {
  CARD = 'CARD',
  MPESA = 'MPESA',
  CASH = 'CASH',
  CHEQUE = 'CHEQUE',
  BANK_TRANSFER = 'BANK_TRANSFER'
}

export interface IClientPolicyPayment {
  id?: number;
  payDate?: Moment;
  amount?: number;
  paymentMethod?: PaymentMethod;
  isIPF?: boolean;
  clientPolicyId?: number;
}

export const defaultValue: Readonly<IClientPolicyPayment> = {
  isIPF: false
};
