import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TpsoaTestModule } from '../../../test.module';
import { LoyaltyCardDetailComponent } from 'app/entities/loyalty-card/loyalty-card-detail.component';
import { LoyaltyCard } from 'app/shared/model/loyalty-card.model';

describe('Component Tests', () => {
  describe('LoyaltyCard Management Detail Component', () => {
    let comp: LoyaltyCardDetailComponent;
    let fixture: ComponentFixture<LoyaltyCardDetailComponent>;
    const route = ({ data: of({ loyaltyCard: new LoyaltyCard(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpsoaTestModule],
        declarations: [LoyaltyCardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LoyaltyCardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoyaltyCardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load loyaltyCard on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loyaltyCard).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
