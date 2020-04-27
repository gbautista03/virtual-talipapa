import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILineItem, LineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';
import { LineItemComponent } from './line-item.component';
import { LineItemDetailComponent } from './line-item-detail.component';
import { LineItemUpdateComponent } from './line-item-update.component';

@Injectable({ providedIn: 'root' })
export class LineItemResolve implements Resolve<ILineItem> {
  constructor(private service: LineItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILineItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lineItem: HttpResponse<LineItem>) => {
          if (lineItem.body) {
            return of(lineItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LineItem());
  }
}

export const lineItemRoute: Routes = [
  {
    path: '',
    component: LineItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LineItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LineItemDetailComponent,
    resolve: {
      lineItem: LineItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LineItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LineItemUpdateComponent,
    resolve: {
      lineItem: LineItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LineItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LineItemUpdateComponent,
    resolve: {
      lineItem: LineItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LineItems'
    },
    canActivate: [UserRouteAccessService]
  }
];
