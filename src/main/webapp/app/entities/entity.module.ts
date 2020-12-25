import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.TpsoaClientModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.TpsoaOrderModule),
      },
      {
        path: 'loyalty-card',
        loadChildren: () => import('./loyalty-card/loyalty-card.module').then(m => m.TpsoaLoyaltyCardModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TpsoaEntityModule {}
