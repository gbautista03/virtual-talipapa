import { IItem } from 'app/shared/model/item.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ILineItem {
  id?: number;
  quantity?: number;
  item?: IItem;
  discount?: IDiscount;
  order?: IOrder;
}

export class LineItem implements ILineItem {
  constructor(public id?: number, public quantity?: number, public item?: IItem, public discount?: IDiscount, public order?: IOrder) {}
}
