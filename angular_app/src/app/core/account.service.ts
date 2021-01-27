import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {Constants} from '../constants';
import {UserAccount} from '../model/user-account';
import {UserAction} from '../model/user-action';

@Injectable()
export class AccountService {

    constructor(private _httpClient: HttpClient) {
    }

    getUserAccount(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account');
    }

    getUserAllInfo(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account/accountInfo');
    }

    postUserAction(userAction: UserAction): Observable<string> {
        return this._httpClient.post<string>(Constants.apiRoot + 'game/userAction', userAction);
    }

    finalizeRegister(userAccount: UserAccount) {
        return this._httpClient.post(`${Constants.apiRoot}account/finalize`, userAccount);
    }
}
