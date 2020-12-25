import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILoyaltyCard } from 'app/shared/model/loyalty-card.model';

type EntityResponseType = HttpResponse<ILoyaltyCard>;
type EntityArrayResponseType = HttpResponse<ILoyaltyCard[]>;

@Injectable({ providedIn: 'root' })
export class LoyaltyCardService {
  public resourceUrl = SERVER_API_URL + 'api/loyalty-cards';

  constructor(protected http: HttpClient) {}

  create(loyaltyCard: ILoyaltyCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loyaltyCard);
    return this.http
      .post<ILoyaltyCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(loyaltyCard: ILoyaltyCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loyaltyCard);
    return this.http
      .put<ILoyaltyCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILoyaltyCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoyaltyCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(loyaltyCard: ILoyaltyCard): ILoyaltyCard {
    const copy: ILoyaltyCard = Object.assign({}, loyaltyCard, {
      createdAt: loyaltyCard.createdAt && loyaltyCard.createdAt.isValid() ? loyaltyCard.createdAt.toJSON() : undefined,
      expiredAt: loyaltyCard.expiredAt && loyaltyCard.expiredAt.isValid() ? loyaltyCard.expiredAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.expiredAt = res.body.expiredAt ? moment(res.body.expiredAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((loyaltyCard: ILoyaltyCard) => {
        loyaltyCard.createdAt = loyaltyCard.createdAt ? moment(loyaltyCard.createdAt) : undefined;
        loyaltyCard.expiredAt = loyaltyCard.expiredAt ? moment(loyaltyCard.expiredAt) : undefined;
      });
    }
    return res;
  }
}
