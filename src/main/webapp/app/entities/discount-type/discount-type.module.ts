import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VirtualTalipapaAppSharedModule } from 'app/shared/shared.module';
import { DiscountTypeComponent } from './discount-type.component';
import { DiscountTypeDetailComponent } from './discount-type-detail.component';
import { DiscountTypeUpdateComponent } from './discount-type-update.component';
import { DiscountTypeDeleteDialogComponent } from './discount-type-delete-dialog.component';
import { discountTypeRoute } from './discount-type.route';

@NgModule({
  imports: [VirtualTalipapaAppSharedModule, RouterModule.forChild(discountTypeRoute)],
  declarations: [DiscountTypeComponent, DiscountTypeDetailComponent, DiscountTypeUpdateComponent, DiscountTypeDeleteDialogComponent],
  entryComponents: [DiscountTypeDeleteDialogComponent]
})
export class VirtualTalipapaAppDiscountTypeModule {}
