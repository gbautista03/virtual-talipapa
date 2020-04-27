import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { VirtualTalipapaAppSharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [VirtualTalipapaAppSharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent]
})
export class LogsModule {}
