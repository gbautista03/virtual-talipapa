import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IItemType } from 'app/shared/model/item-type.model';

@Component({
  selector: 'jhi-item-type-detail',
  templateUrl: './item-type-detail.component.html'
})
export class ItemTypeDetailComponent implements OnInit {
  itemType: IItemType | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemType }) => (this.itemType = itemType));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
