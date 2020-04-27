import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VirtualTalipapaAppSharedModule } from 'app/shared/shared.module';
import { ItemTypeComponent } from './item-type.component';
import { ItemTypeDetailComponent } from './item-type-detail.component';
import { ItemTypeUpdateComponent } from './item-type-update.component';
import { ItemTypeDeleteDialogComponent } from './item-type-delete-dialog.component';
import { itemTypeRoute } from './item-type.route';

@NgModule({
  imports: [VirtualTalipapaAppSharedModule, RouterModule.forChild(itemTypeRoute)],
  declarations: [ItemTypeComponent, ItemTypeDetailComponent, ItemTypeUpdateComponent, ItemTypeDeleteDialogComponent],
  entryComponents: [ItemTypeDeleteDialogComponent]
})
export class VirtualTalipapaAppItemTypeModule {}
