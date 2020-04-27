export interface IDiscountType {
  id?: number;
  name?: string;
  modifier?: string;
}

export class DiscountType implements IDiscountType {
  constructor(public id?: number, public name?: string, public modifier?: string) {}
}
