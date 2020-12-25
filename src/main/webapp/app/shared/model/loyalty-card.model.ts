import { Moment } from 'moment';

export interface ILoyaltyCard {
  id?: number;
  rewardPoints?: number;
  createdAt?: Moment;
  expiredAt?: Moment;
  clientCin?: string;
  clientId?: number;
}

export class LoyaltyCard implements ILoyaltyCard {
  constructor(
    public id?: number,
    public rewardPoints?: number,
    public createdAt?: Moment,
    public expiredAt?: Moment,
    public clientCin?: string,
    public clientId?: number
  ) {}
}
