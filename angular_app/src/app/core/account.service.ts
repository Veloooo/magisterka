import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {Constants} from '../constants';
import {UserAccount} from '../model/user-account';
import {UserAction} from '../model/user-action';
import {ServerResponse} from '../model/server-response';


@Injectable()
export class AccountService {

    userAccount: UserAccount;

    constructor(private _httpClient: HttpClient) {
        console.log("Service constructor")
        this.getAllUSerInfoNormal();
    }

    getUserAccount(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account');
    }

    getAllUSerInfoNormal(): UserAccount {
        if (!this.userAccount) {
            this._httpClient.get<UserAccount>(Constants.apiRoot + 'account/accountInfo').subscribe(user => {
                this.userAccount = user;
                console.log("Data fetched");
                return this.userAccount
            })
        } else {
            return this.userAccount;
        }
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

    finalizeRegister(userAccount: UserAccount) {
        return this._httpClient.post(`${Constants.apiRoot}account/finalize`, userAccount);
    }
}
