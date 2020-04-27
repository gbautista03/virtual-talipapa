import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiscountType } from 'app/shared/model/discount-type.model';
import { DiscountTypeService } from './discount-type.service';

@Component({
  templateUrl: './discount-type-delete-dialog.component.html'
})
export class DiscountTypeDeleteDialogComponent {
  discountType?: IDiscountType;

  constructor(
    protected discountTypeService: DiscountTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.discountTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('discountTypeListModification');
      this.activeModal.close();
    });
  }
}
