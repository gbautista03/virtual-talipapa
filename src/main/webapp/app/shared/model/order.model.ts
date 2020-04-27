import { Moment } from 'moment';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { ILineItem } from 'app/shared/model/line-item.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrder {
  id?: number;
  orderNumber?: number;
  orderDate?: Moment;
  processDate?: Moment;
  handler?: IUserExtra;
  discount?: IDiscount;
  items?: ILineItem[];
  customer?: ICustomer;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderNumber?: number,
    public orderDate?: Moment,
    public processDate?: Moment,
    public handler?: IUserExtra,
    public discount?: IDiscount,
    public items?: ILineItem[],
    public customer?: ICustomer
  ) {}
}
