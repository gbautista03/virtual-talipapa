import { IUser } from 'app/core/user/user.model';

export interface IUserExtra {
  id?: number;
  fullName?: string;
  contactNumber?: string;
  parentUser?: IUser;
}

export class UserExtra implements IUserExtra {
  constructor(public id?: number, public fullName?: string, public contactNumber?: string, public parentUser?: IUser) {}
}
