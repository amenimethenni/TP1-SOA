import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpsoaTestModule } from '../../../test.module';
import { LoyaltyCardComponent } from 'app/entities/loyalty-card/loyalty-card.component';
import { LoyaltyCardService } from 'app/entities/loyalty-card/loyalty-card.service';
import { LoyaltyCard } from 'app/shared/model/loyalty-card.model';

describe('Component Tests', () => {
  describe('LoyaltyCard Management Component', () => {
    let comp: LoyaltyCardComponent;
    let fixture: ComponentFixture<LoyaltyCardComponent>;
    let service: LoyaltyCardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpsoaTestModule],
        declarations: [LoyaltyCardComponent],
      })
        .overrideTemplate(LoyaltyCardComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoyaltyCardComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoyaltyCardService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LoyaltyCard(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.loyaltyCards && comp.loyaltyCards[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
