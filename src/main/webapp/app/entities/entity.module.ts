import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.VirtualTalipapaAppRegionModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.VirtualTalipapaAppCountryModule)
      },
      {
        path: 'discount',
        loadChildren: () => import('./discount/discount.module').then(m => m.VirtualTalipapaAppDiscountModule)
      },
      {
        path: 'discount-type',
        loadChildren: () => import('./discount-type/discount-type.module').then(m => m.VirtualTalipapaAppDiscountTypeModule)
      },
      {
        path: 'unit-of-measure',
        loadChildren: () => import('./unit-of-measure/unit-of-measure.module').then(m => m.VirtualTalipapaAppUnitOfMeasureModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.VirtualTalipapaAppAddressModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.VirtualTalipapaAppOrderModule)
      },
      {
        path: 'line-item',
        loadChildren: () => import('./line-item/line-item.module').then(m => m.VirtualTalipapaAppLineItemModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.VirtualTalipapaAppCustomerModule)
      },
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.VirtualTalipapaAppItemModule)
      },
      {
        path: 'item-type',
        loadChildren: () => import('./item-type/item-type.module').then(m => m.VirtualTalipapaAppItemTypeModule)
      },
      {
        path: 'user-extra',
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.VirtualTalipapaAppUserExtraModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class VirtualTalipapaAppEntityModule {}
