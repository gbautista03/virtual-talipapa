import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiscountType, DiscountType } from 'app/shared/model/discount-type.model';
import { DiscountTypeService } from './discount-type.service';

@Component({
  selector: 'jhi-discount-type-update',
  templateUrl: './discount-type-update.component.html'
})
export class DiscountTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    modifier: []
  });

  constructor(protected discountTypeService: DiscountTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discountType }) => {
      this.updateForm(discountType);
    });
  }

  updateForm(discountType: IDiscountType): void {
    this.editForm.patchValue({
      id: discountType.id,
      name: discountType.name,
      modifier: discountType.modifier
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discountType = this.createFromForm();
    if (discountType.id !== undefined) {
      this.subscribeToSaveResponse(this.discountTypeService.update(discountType));
    } else {
      this.subscribeToSaveResponse(this.discountTypeService.create(discountType));
    }
  }

  private createFromForm(): IDiscountType {
    return {
      ...new DiscountType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      modifier: this.editForm.get(['modifier'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscountType>>): void {
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
}
