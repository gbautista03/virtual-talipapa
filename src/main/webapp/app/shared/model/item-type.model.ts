export interface IItemType {
  id?: number;
  type?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
}

export class ItemType implements IItemType {
  constructor(
    public id?: number,
    public type?: string,
    public description?: string,
    public imageContentType?: string,
    public image?: any
  ) {}
}
