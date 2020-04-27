import { ICountry } from 'app/shared/model/country.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  name?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  landmark?: string;
  country?: ICountry;
  customer?: ICustomer;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public name?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public landmark?: string,
    public country?: ICountry,
    public customer?: ICustomer
  ) {}
}
