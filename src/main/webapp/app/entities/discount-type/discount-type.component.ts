import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiscountType } from 'app/shared/model/discount-type.model';
import { DiscountTypeService } from './discount-type.service';
import { DiscountTypeDeleteDialogComponent } from './discount-type-delete-dialog.component';

@Component({
  selector: 'jhi-discount-type',
  templateUrl: './discount-type.component.html'
})
export class DiscountTypeComponent implements OnInit, OnDestroy {
  discountTypes?: IDiscountType[];
  eventSubscriber?: Subscription;

  constructor(
    protected discountTypeService: DiscountTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.discountTypeService.query().subscribe((res: HttpResponse<IDiscountType[]>) => (this.discountTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDiscountTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDiscountType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDiscountTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('discountTypeListModification', () => this.loadAll());
  }

  delete(discountType: IDiscountType): void {
    const modalRef = this.modalService.open(DiscountTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.discountType = discountType;
  }
}
