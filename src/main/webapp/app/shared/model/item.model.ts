import { IItemType } from 'app/shared/model/item-type.model';
import { IUnitOfMeasure } from 'app/shared/model/unit-of-measure.model';

export interface IItem {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  imageContentType?: string;
  image?: any;
  type?: IItemType;
  units?: IUnitOfMeasure[];
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public price?: number,
    public imageContentType?: string,
    public image?: any,
    public type?: IItemType,
    public units?: IUnitOfMeasure[]
  ) {}
}
