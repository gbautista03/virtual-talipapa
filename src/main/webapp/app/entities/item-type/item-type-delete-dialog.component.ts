import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemType } from 'app/shared/model/item-type.model';
import { ItemTypeService } from './item-type.service';

@Component({
  templateUrl: './item-type-delete-dialog.component.html'
})
export class ItemTypeDeleteDialogComponent {
  itemType?: IItemType;

  constructor(protected itemTypeService: ItemTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemTypeListModification');
      this.activeModal.close();
    });
  }
}
