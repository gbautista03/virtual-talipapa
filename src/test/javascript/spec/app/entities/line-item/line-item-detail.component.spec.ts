import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VirtualTalipapaAppTestModule } from '../../../test.module';
import { LineItemDetailComponent } from 'app/entities/line-item/line-item-detail.component';
import { LineItem } from 'app/shared/model/line-item.model';

describe('Component Tests', () => {
  describe('LineItem Management Detail Component', () => {
    let comp: LineItemDetailComponent;
    let fixture: ComponentFixture<LineItemDetailComponent>;
    const route = ({ data: of({ lineItem: new LineItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VirtualTalipapaAppTestModule],
        declarations: [LineItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LineItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lineItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lineItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
