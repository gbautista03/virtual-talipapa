import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { VirtualTalipapaAppTestModule } from '../../../test.module';
import { ItemTypeDetailComponent } from 'app/entities/item-type/item-type-detail.component';
import { ItemType } from 'app/shared/model/item-type.model';

describe('Component Tests', () => {
  describe('ItemType Management Detail Component', () => {
    let comp: ItemTypeDetailComponent;
    let fixture: ComponentFixture<ItemTypeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ itemType: new ItemType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VirtualTalipapaAppTestModule],
        declarations: [ItemTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemTypeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load itemType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
