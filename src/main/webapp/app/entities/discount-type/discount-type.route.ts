import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiscountType, DiscountType } from 'app/shared/model/discount-type.model';
import { DiscountTypeService } from './discount-type.service';
import { DiscountTypeComponent } from './discount-type.component';
import { DiscountTypeDetailComponent } from './discount-type-detail.component';
import { DiscountTypeUpdateComponent } from './discount-type-update.component';

@Injectable({ providedIn: 'root' })
export class DiscountTypeResolve implements Resolve<IDiscountType> {
  constructor(private service: DiscountTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiscountType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((discountType: HttpResponse<DiscountType>) => {
          if (discountType.body) {
            return of(discountType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DiscountType());
  }
}

export const discountTypeRoute: Routes = [
  {
    path: '',
    component: DiscountTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DiscountTypeDetailComponent,
    resolve: {
      discountType: DiscountTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DiscountTypeUpdateComponent,
    resolve: {
      discountType: DiscountTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DiscountTypeUpdateComponent,
    resolve: {
      discountType: DiscountTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
