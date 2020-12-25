import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILoyaltyCard, LoyaltyCard } from 'app/shared/model/loyalty-card.model';
import { LoyaltyCardService } from './loyalty-card.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-loyalty-card-update',
  templateUrl: './loyalty-card-update.component.html',
})
export class LoyaltyCardUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    rewardPoints: [null, [Validators.required, Validators.min(0)]],
    createdAt: [null, [Validators.required]],
    expiredAt: [null, [Validators.required]],
    clientId: [],
  });

  constructor(
    protected loyaltyCardService: LoyaltyCardService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loyaltyCard }) => {
      if (!loyaltyCard.id) {
        const today = moment().startOf('day');
        loyaltyCard.createdAt = today;
        loyaltyCard.expiredAt = today;
      }

      this.updateForm(loyaltyCard);

      this.clientService
        .query({ filter: 'loyaltycard-is-null' })
        .pipe(
          map((res: HttpResponse<IClient[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IClient[]) => {
          if (!loyaltyCard.clientId) {
            this.clients = resBody;
          } else {
            this.clientService
              .find(loyaltyCard.clientId)
              .pipe(
                map((subRes: HttpResponse<IClient>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IClient[]) => (this.clients = concatRes));
          }
        });
    });
  }

  updateForm(loyaltyCard: ILoyaltyCard): void {
    this.editForm.patchValue({
      id: loyaltyCard.id,
      rewardPoints: loyaltyCard.rewardPoints,
      createdAt: loyaltyCard.createdAt ? loyaltyCard.createdAt.format(DATE_TIME_FORMAT) : null,
      expiredAt: loyaltyCard.expiredAt ? loyaltyCard.expiredAt.format(DATE_TIME_FORMAT) : null,
      clientId: loyaltyCard.clientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loyaltyCard = this.createFromForm();
    if (loyaltyCard.id !== undefined) {
      this.subscribeToSaveResponse(this.loyaltyCardService.update(loyaltyCard));
    } else {
      this.subscribeToSaveResponse(this.loyaltyCardService.create(loyaltyCard));
    }
  }

  private createFromForm(): ILoyaltyCard {
    return {
      ...new LoyaltyCard(),
      id: this.editForm.get(['id'])!.value,
      rewardPoints: this.editForm.get(['rewardPoints'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      expiredAt: this.editForm.get(['expiredAt'])!.value ? moment(this.editForm.get(['expiredAt'])!.value, DATE_TIME_FORMAT) : undefined,
      clientId: this.editForm.get(['clientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoyaltyCard>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
