import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from 'app/entities/discount/discount.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = IUserExtra | IDiscount | ICustomer;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  handlers: IUserExtra[] = [];
  discounts: IDiscount[] = [];
  customers: ICustomer[] = [];
  processDateDp: any;

  editForm = this.fb.group({
    id: [],
    orderNumber: [null, [Validators.required]],
    orderDate: [null, [Validators.required]],
    processDate: [],
    handler: [],
    discount: [],
    customer: []
  });

  constructor(
    protected orderService: OrderService,
    protected userExtraService: UserExtraService,
    protected discountService: DiscountService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.orderDate = today;
      }

      this.updateForm(order);

      this.userExtraService
        .query({ filter: 'order-is-null' })
        .pipe(
          map((res: HttpResponse<IUserExtra[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IUserExtra[]) => {
          if (!order.handler || !order.handler.id) {
            this.handlers = resBody;
          } else {
            this.userExtraService
              .find(order.handler.id)
              .pipe(
                map((subRes: HttpResponse<IUserExtra>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IUserExtra[]) => (this.handlers = concatRes));
          }
        });

      this.discountService
        .query({ filter: 'order-is-null' })
        .pipe(
          map((res: HttpResponse<IDiscount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDiscount[]) => {
          if (!order.discount || !order.discount.id) {
            this.discounts = resBody;
          } else {
            this.discountService
              .find(order.discount.id)
              .pipe(
                map((subRes: HttpResponse<IDiscount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDiscount[]) => (this.discounts = concatRes));
          }
        });

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderNumber: order.orderNumber,
      orderDate: order.orderDate ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      processDate: order.processDate,
      handler: order.handler,
      discount: order.discount,
      customer: order.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderNumber: this.editForm.get(['orderNumber'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value ? moment(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      processDate: this.editForm.get(['processDate'])!.value,
      handler: this.editForm.get(['handler'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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
