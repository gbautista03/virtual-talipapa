import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemType } from 'app/shared/model/item-type.model';
import { ItemTypeService } from './item-type.service';
import { ItemTypeDeleteDialogComponent } from './item-type-delete-dialog.component';

@Component({
  selector: 'jhi-item-type',
  templateUrl: './item-type.component.html'
})
export class ItemTypeComponent implements OnInit, OnDestroy {
  itemTypes?: IItemType[];
  eventSubscriber?: Subscription;

  constructor(
    protected itemTypeService: ItemTypeService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.itemTypeService.query().subscribe((res: HttpResponse<IItemType[]>) => (this.itemTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInItemTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IItemType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInItemTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('itemTypeListModification', () => this.loadAll());
  }

  delete(itemType: IItemType): void {
    const modalRef = this.modalService.open(ItemTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.itemType = itemType;
  }
}
