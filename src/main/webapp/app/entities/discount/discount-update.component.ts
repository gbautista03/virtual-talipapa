import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDiscount, Discount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';
import { IDiscountType } from 'app/shared/model/discount-type.model';
import { DiscountTypeService } from 'app/entities/discount-type/discount-type.service';

@Component({
  selector: 'jhi-discount-update',
  templateUrl: './discount-update.component.html'
})
export class DiscountUpdateComponent implements OnInit {
  isSaving = false;
  types: IDiscountType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    value: [null, [Validators.required]],
    type: []
  });

  constructor(
    protected discountService: DiscountService,
    protected discountTypeService: DiscountTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discount }) => {
      this.updateForm(discount);

      this.discountTypeService
        .query({ filter: 'discount-is-null' })
        .pipe(
          map((res: HttpResponse<IDiscountType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDiscountType[]) => {
          if (!discount.type || !discount.type.id) {
            this.types = resBody;
          } else {
            this.discountTypeService
              .find(discount.type.id)
              .pipe(
                map((subRes: HttpResponse<IDiscountType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDiscountType[]) => (this.types = concatRes));
          }
        });
    });
  }

  updateForm(discount: IDiscount): void {
    this.editForm.patchValue({
      id: discount.id,
      name: discount.name,
      value: discount.value,
      type: discount.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discount = this.createFromForm();
    if (discount.id !== undefined) {
      this.subscribeToSaveResponse(this.discountService.update(discount));
    } else {
      this.subscribeToSaveResponse(this.discountService.create(discount));
    }
  }

  private createFromForm(): IDiscount {
    return {
      ...new Discount(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscount>>): void {
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

  trackById(index: number, item: IDiscountType): any {
    return item.id;
  }
}
