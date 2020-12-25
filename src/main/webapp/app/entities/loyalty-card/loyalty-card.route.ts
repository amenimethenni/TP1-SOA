import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILoyaltyCard, LoyaltyCard } from 'app/shared/model/loyalty-card.model';
import { LoyaltyCardService } from './loyalty-card.service';
import { LoyaltyCardComponent } from './loyalty-card.component';
import { LoyaltyCardDetailComponent } from './loyalty-card-detail.component';
import { LoyaltyCardUpdateComponent } from './loyalty-card-update.component';

@Injectable({ providedIn: 'root' })
export class LoyaltyCardResolve implements Resolve<ILoyaltyCard> {
  constructor(private service: LoyaltyCardService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoyaltyCard> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((loyaltyCard: HttpResponse<LoyaltyCard>) => {
          if (loyaltyCard.body) {
            return of(loyaltyCard.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LoyaltyCard());
  }
}

export const loyaltyCardRoute: Routes = [
  {
    path: '',
    component: LoyaltyCardComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tpsoaApp.loyaltyCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoyaltyCardDetailComponent,
    resolve: {
      loyaltyCard: LoyaltyCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tpsoaApp.loyaltyCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoyaltyCardUpdateComponent,
    resolve: {
      loyaltyCard: LoyaltyCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tpsoaApp.loyaltyCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoyaltyCardUpdateComponent,
    resolve: {
      loyaltyCard: LoyaltyCardResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tpsoaApp.loyaltyCard.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
