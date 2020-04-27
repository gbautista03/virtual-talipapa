import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemType } from 'app/shared/model/item-type.model';

type EntityResponseType = HttpResponse<IItemType>;
type EntityArrayResponseType = HttpResponse<IItemType[]>;

@Injectable({ providedIn: 'root' })
export class ItemTypeService {
  public resourceUrl = SERVER_API_URL + 'api/item-types';

  constructor(protected http: HttpClient) {}

  create(itemType: IItemType): Observable<EntityResponseType> {
    return this.http.post<IItemType>(this.resourceUrl, itemType, { observe: 'response' });
  }

  update(itemType: IItemType): Observable<EntityResponseType> {
    return this.http.put<IItemType>(this.resourceUrl, itemType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
