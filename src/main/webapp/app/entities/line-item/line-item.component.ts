import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';
import { LineItemDeleteDialogComponent } from './line-item-delete-dialog.component';

@Component({
  selector: 'jhi-line-item',
  templateUrl: './line-item.component.html'
})
export class LineItemComponent implements OnInit, OnDestroy {
  lineItems?: ILineItem[];
  eventSubscriber?: Subscription;

  constructor(protected lineItemService: LineItemService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lineItemService.query().subscribe((res: HttpResponse<ILineItem[]>) => (this.lineItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLineItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILineItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLineItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('lineItemListModification', () => this.loadAll());
  }

  delete(lineItem: ILineItem): void {
    const modalRef = this.modalService.open(LineItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lineItem = lineItem;
  }
}
