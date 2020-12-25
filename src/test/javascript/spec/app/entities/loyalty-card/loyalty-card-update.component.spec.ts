import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TpsoaTestModule } from '../../../test.module';
import { LoyaltyCardUpdateComponent } from 'app/entities/loyalty-card/loyalty-card-update.component';
import { LoyaltyCardService } from 'app/entities/loyalty-card/loyalty-card.service';
import { LoyaltyCard } from 'app/shared/model/loyalty-card.model';

describe('Component Tests', () => {
  describe('LoyaltyCard Management Update Component', () => {
    let comp: LoyaltyCardUpdateComponent;
    let fixture: ComponentFixture<LoyaltyCardUpdateComponent>;
    let service: LoyaltyCardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpsoaTestModule],
        declarations: [LoyaltyCardUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LoyaltyCardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoyaltyCardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoyaltyCardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoyaltyCard(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoyaltyCard();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
