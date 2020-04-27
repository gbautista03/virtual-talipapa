import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VirtualTalipapaAppTestModule } from '../../../test.module';
import { ItemTypeUpdateComponent } from 'app/entities/item-type/item-type-update.component';
import { ItemTypeService } from 'app/entities/item-type/item-type.service';
import { ItemType } from 'app/shared/model/item-type.model';

describe('Component Tests', () => {
  describe('ItemType Management Update Component', () => {
    let comp: ItemTypeUpdateComponent;
    let fixture: ComponentFixture<ItemTypeUpdateComponent>;
    let service: ItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VirtualTalipapaAppTestModule],
        declarations: [ItemTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemType(123);
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
        const entity = new ItemType();
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
