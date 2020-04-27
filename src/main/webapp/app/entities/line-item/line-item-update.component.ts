import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILineItem, LineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from 'app/entities/discount/discount.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IItem | IDiscount | IOrder;

@Component({
  selector: 'jhi-line-item-update',
  templateUrl: './line-item-update.component.html'
})
export class LineItemUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];
  discounts: IDiscount[] = [];
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    item: [],
    discount: [],
    order: []
  });

  constructor(
    protected lineItemService: LineItemService,
    protected itemService: ItemService,
    protected discountService: DiscountService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineItem }) => {
      this.updateForm(lineItem);

      this.itemService
        .query({ filter: 'lineitem-is-null' })
        .pipe(
          map((res: HttpResponse<IItem[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IItem[]) => {
          if (!lineItem.item || !lineItem.item.id) {
            this.items = resBody;
          } else {
            this.itemService
              .find(lineItem.item.id)
              .pipe(
                map((subRes: HttpResponse<IItem>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IItem[]) => (this.items = concatRes));
          }
        });

      this.discountService
        .query({ filter: 'lineitem-is-null' })
        .pipe(
          map((res: HttpResponse<IDiscount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDiscount[]) => {
          if (!lineItem.discount || !lineItem.discount.id) {
            this.discounts = resBody;
          } else {
            this.discountService
              .find(lineItem.discount.id)
              .pipe(
                map((subRes: HttpResponse<IDiscount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDiscount[]) => (this.discounts = concatRes));
          }
        });

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(lineItem: ILineItem): void {
    this.editForm.patchValue({
      id: lineItem.id,
      quantity: lineItem.quantity,
      item: lineItem.item,
      discount: lineItem.discount,
      order: lineItem.order
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lineItem = this.createFromForm();
    if (lineItem.id !== undefined) {
      this.subscribeToSaveResponse(this.lineItemService.update(lineItem));
    } else {
      this.subscribeToSaveResponse(this.lineItemService.create(lineItem));
    }
  }

  private createFromForm(): ILineItem {
    return {
      ...new LineItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      item: this.editForm.get(['item'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      order: this.editForm.get(['order'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
