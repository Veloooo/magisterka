import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';

import {Constants} from '../constants';
import {UserAccount} from '../model/user-account';
import {UserAction} from '../model/user-action';
import {ServerResponse} from '../model/server-response';
import {tap} from 'rxjs/operators';


@Injectable()
export class AccountService {

    get userAccount() {
        return JSON.parse(localStorage.getItem('user'));
    };

    constructor(private _httpClient: HttpClient) {
    }


    getAllUSerInfoNormal(forceBackendCall: boolean): Observable<UserAccount> {
        if (this.userAccount && !forceBackendCall) {
            return of(this.userAccount);
        }

        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account/accountInfo')
            .pipe(
                tap(user => localStorage.setItem('user', JSON.stringify(user)))
            );

    }


    getUserAccount(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account');
    }


    getUserAllInfo(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account/accountInfo');
    }

    populationAction(userAction: UserAction): Observable<ServerResponse> {
        return this._httpClient.post<ServerResponse>(Constants.apiRoot + 'game/population', userAction);
    }

    resourcesAction(userAction: UserAction): Observable<ServerResponse> {
        return this._httpClient.post<ServerResponse>(Constants.apiRoot + 'game/resources', userAction);
    }

    researchAction(userAction: UserAction): Observable<ServerResponse> {
        return this._httpClient.post<ServerResponse>(Constants.apiRoot + 'game/research', userAction);
    }

    buildingsAction(userAction: UserAction): Observable<ServerResponse> {
        return this._httpClient.post<ServerResponse>(Constants.apiRoot + 'game/buildings', userAction);
    }
    heroAction(userAction: UserAction): Observable<ServerResponse> {
        return this._httpClient.post<ServerResponse>(Constants.apiRoot + 'game/tavern', userAction);
    }

    finalizeRegister(userAccount: UserAccount) {
        return this._httpClient.post(`${Constants.apiRoot}account/finalize`, userAccount);
    }
}
