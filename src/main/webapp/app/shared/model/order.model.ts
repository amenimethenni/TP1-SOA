import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  date?: Moment;
  status?: string;
  price?: number;
  clientCin?: string;
  clientId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public date?: Moment,
    public status?: string,
    public price?: number,
    public clientCin?: string,
    public clientId?: number
  ) {}
}
