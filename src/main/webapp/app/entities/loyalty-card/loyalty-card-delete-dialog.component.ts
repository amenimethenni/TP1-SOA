import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoyaltyCard } from 'app/shared/model/loyalty-card.model';
import { LoyaltyCardService } from './loyalty-card.service';

@Component({
  templateUrl: './loyalty-card-delete-dialog.component.html',
})
export class LoyaltyCardDeleteDialogComponent {
  loyaltyCard?: ILoyaltyCard;

  constructor(
    protected loyaltyCardService: LoyaltyCardService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loyaltyCardService.delete(id).subscribe(() => {
      this.eventManager.broadcast('loyaltyCardListModification');
      this.activeModal.close();
    });
  }
}
