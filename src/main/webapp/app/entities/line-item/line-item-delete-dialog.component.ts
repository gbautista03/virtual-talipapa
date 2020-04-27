import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';

@Component({
  templateUrl: './line-item-delete-dialog.component.html'
})
export class LineItemDeleteDialogComponent {
  lineItem?: ILineItem;

  constructor(protected lineItemService: LineItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lineItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lineItemListModification');
      this.activeModal.close();
    });
  }
}
