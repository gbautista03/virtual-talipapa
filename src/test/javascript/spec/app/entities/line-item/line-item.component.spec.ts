import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VirtualTalipapaAppTestModule } from '../../../test.module';
import { LineItemComponent } from 'app/entities/line-item/line-item.component';
import { LineItemService } from 'app/entities/line-item/line-item.service';
import { LineItem } from 'app/shared/model/line-item.model';

describe('Component Tests', () => {
  describe('LineItem Management Component', () => {
    let comp: LineItemComponent;
    let fixture: ComponentFixture<LineItemComponent>;
    let service: LineItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VirtualTalipapaAppTestModule],
        declarations: [LineItemComponent]
      })
        .overrideTemplate(LineItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LineItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lineItems && comp.lineItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
