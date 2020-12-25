import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoyaltyCard } from 'app/shared/model/loyalty-card.model';

@Component({
  selector: 'jhi-loyalty-card-detail',
  templateUrl: './loyalty-card-detail.component.html',
})
export class LoyaltyCardDetailComponent implements OnInit {
  loyaltyCard: ILoyaltyCard | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loyaltyCard }) => (this.loyaltyCard = loyaltyCard));
  }

  previousState(): void {
    window.history.back();
  }
}
