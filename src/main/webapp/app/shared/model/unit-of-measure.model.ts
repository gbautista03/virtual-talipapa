import { IItem } from 'app/shared/model/item.model';

export interface IUnitOfMeasure {
  id?: number;
  name?: string;
  abbreviation?: string;
  item?: IItem;
}

export class UnitOfMeasure implements IUnitOfMeasure {
  constructor(public id?: number, public name?: string, public abbreviation?: string, public item?: IItem) {}
}
