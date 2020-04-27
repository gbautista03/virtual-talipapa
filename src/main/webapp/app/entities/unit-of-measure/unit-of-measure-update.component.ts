import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUnitOfMeasure, UnitOfMeasure } from 'app/shared/model/unit-of-measure.model';
import { UnitOfMeasureService } from './unit-of-measure.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';

@Component({
  selector: 'jhi-unit-of-measure-update',
  templateUrl: './unit-of-measure-update.component.html'
})
export class UnitOfMeasureUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    abbreviation: [null, [Validators.required]],
    item: []
  });

  constructor(
    protected unitOfMeasureService: UnitOfMeasureService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitOfMeasure }) => {
      this.updateForm(unitOfMeasure);

      this.itemService.query().subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));
    });
  }

  updateForm(unitOfMeasure: IUnitOfMeasure): void {
    this.editForm.patchValue({
      id: unitOfMeasure.id,
      name: unitOfMeasure.name,
      abbreviation: unitOfMeasure.abbreviation,
      item: unitOfMeasure.item
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unitOfMeasure = this.createFromForm();
    if (unitOfMeasure.id !== undefined) {
      this.subscribeToSaveResponse(this.unitOfMeasureService.update(unitOfMeasure));
    } else {
      this.subscribeToSaveResponse(this.unitOfMeasureService.create(unitOfMeasure));
    }
  }

  private createFromForm(): IUnitOfMeasure {
    return {
      ...new UnitOfMeasure(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      abbreviation: this.editForm.get(['abbreviation'])!.value,
      item: this.editForm.get(['item'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnitOfMeasure>>): void {
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

  trackById(index: number, item: IItem): any {
    return item.id;
  }
}
