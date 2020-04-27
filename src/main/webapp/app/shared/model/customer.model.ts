import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { IAddress } from 'app/shared/model/address.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  joinDate?: Moment;
  orders?: IOrder[];
  addresses?: IAddress[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public joinDate?: Moment,
    public orders?: IOrder[],
    public addresses?: IAddress[]
  ) {}
}
