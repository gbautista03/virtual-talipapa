import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { VirtualTalipapaAppSharedModule } from 'app/shared/shared.module';
import { VirtualTalipapaAppCoreModule } from 'app/core/core.module';
import { VirtualTalipapaAppAppRoutingModule } from './app-routing.module';
import { VirtualTalipapaAppHomeModule } from './home/home.module';
import { VirtualTalipapaAppEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    VirtualTalipapaAppSharedModule,
    VirtualTalipapaAppCoreModule,
    VirtualTalipapaAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VirtualTalipapaAppEntityModule,
    VirtualTalipapaAppAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class VirtualTalipapaAppAppModule {}
