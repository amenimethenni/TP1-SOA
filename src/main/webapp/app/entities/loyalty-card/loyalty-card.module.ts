import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpsoaSharedModule } from 'app/shared/shared.module';
import { LoyaltyCardComponent } from './loyalty-card.component';
import { LoyaltyCardDetailComponent } from './loyalty-card-detail.component';
import { LoyaltyCardUpdateComponent } from './loyalty-card-update.component';
import { LoyaltyCardDeleteDialogComponent } from './loyalty-card-delete-dialog.component';
import { loyaltyCardRoute } from './loyalty-card.route';

@NgModule({
  imports: [TpsoaSharedModule, RouterModule.forChild(loyaltyCardRoute)],
  declarations: [LoyaltyCardComponent, LoyaltyCardDetailComponent, LoyaltyCardUpdateComponent, LoyaltyCardDeleteDialogComponent],
  entryComponents: [LoyaltyCardDeleteDialogComponent],
})
export class TpsoaLoyaltyCardModule {}
