import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnitOfMeasure } from 'app/shared/model/unit-of-measure.model';
import { UnitOfMeasureService } from './unit-of-measure.service';
import { UnitOfMeasureDeleteDialogComponent } from './unit-of-measure-delete-dialog.component';

@Component({
  selector: 'jhi-unit-of-measure',
  templateUrl: './unit-of-measure.component.html'
})
export class UnitOfMeasureComponent implements OnInit, OnDestroy {
  unitOfMeasures?: IUnitOfMeasure[];
  eventSubscriber?: Subscription;

  constructor(
    protected unitOfMeasureService: UnitOfMeasureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.unitOfMeasureService.query().subscribe((res: HttpResponse<IUnitOfMeasure[]>) => (this.unitOfMeasures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUnitOfMeasures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUnitOfMeasure): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUnitOfMeasures(): void {
    this.eventSubscriber = this.eventManager.subscribe('unitOfMeasureListModification', () => this.loadAll());
  }

  delete(unitOfMeasure: IUnitOfMeasure): void {
    const modalRef = this.modalService.open(UnitOfMeasureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unitOfMeasure = unitOfMeasure;
  }
}
