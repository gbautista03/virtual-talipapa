import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VirtualTalipapaAppTestModule } from '../../../test.module';
import { ItemTypeComponent } from 'app/entities/item-type/item-type.component';
import { ItemTypeService } from 'app/entities/item-type/item-type.service';
import { ItemType } from 'app/shared/model/item-type.model';

describe('Component Tests', () => {
  describe('ItemType Management Component', () => {
    let comp: ItemTypeComponent;
    let fixture: ComponentFixture<ItemTypeComponent>;
    let service: ItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VirtualTalipapaAppTestModule],
        declarations: [ItemTypeComponent]
      })
        .overrideTemplate(ItemTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemTypes && comp.itemTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
