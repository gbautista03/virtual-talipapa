import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemType, ItemType } from 'app/shared/model/item-type.model';
import { ItemTypeService } from './item-type.service';
import { ItemTypeComponent } from './item-type.component';
import { ItemTypeDetailComponent } from './item-type-detail.component';
import { ItemTypeUpdateComponent } from './item-type-update.component';

@Injectable({ providedIn: 'root' })
export class ItemTypeResolve implements Resolve<IItemType> {
  constructor(private service: ItemTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemType: HttpResponse<ItemType>) => {
          if (itemType.body) {
            return of(itemType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemType());
  }
}

export const itemTypeRoute: Routes = [
  {
    path: '',
    component: ItemTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemTypeDetailComponent,
    resolve: {
      itemType: ItemTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemTypeUpdateComponent,
    resolve: {
      itemType: ItemTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemTypeUpdateComponent,
    resolve: {
      itemType: ItemTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
