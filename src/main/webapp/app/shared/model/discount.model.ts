import { IDiscountType } from 'app/shared/model/discount-type.model';

export interface IDiscount {
  id?: number;
  name?: string;
  value?: number;
  type?: IDiscountType;
}

export class Discount implements IDiscount {
  constructor(public id?: number, public name?: string, public value?: number, public type?: IDiscountType) {}
}
