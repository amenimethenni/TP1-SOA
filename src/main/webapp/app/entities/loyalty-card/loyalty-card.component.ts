import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoyaltyCard } from 'app/shared/model/loyalty-card.model';
import { LoyaltyCardService } from './loyalty-card.service';
import { LoyaltyCardDeleteDialogComponent } from './loyalty-card-delete-dialog.component';

@Component({
  selector: 'jhi-loyalty-card',
  templateUrl: './loyalty-card.component.html',
})
export class LoyaltyCardComponent implements OnInit, OnDestroy {
  loyaltyCards?: ILoyaltyCard[];
  eventSubscriber?: Subscription;

  constructor(
    protected loyaltyCardService: LoyaltyCardService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.loyaltyCardService.query().subscribe((res: HttpResponse<ILoyaltyCard[]>) => (this.loyaltyCards = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLoyaltyCards();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILoyaltyCard): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLoyaltyCards(): void {
    this.eventSubscriber = this.eventManager.subscribe('loyaltyCardListModification', () => this.loadAll());
  }

  delete(loyaltyCard: ILoyaltyCard): void {
    const modalRef = this.modalService.open(LoyaltyCardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.loyaltyCard = loyaltyCard;
  }
}
