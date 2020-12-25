import { IOrder } from 'app/shared/model/order.model';

export interface IClient {
  id?: number;
  firstName?: string;
  lastName?: string;
  cin?: string;
  phoneNumber?: string;
  orders?: IOrder[];
  loyaltyCardId?: number;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public cin?: string,
    public phoneNumber?: string,
    public orders?: IOrder[],
    public loyaltyCardId?: number
  ) {}
}
