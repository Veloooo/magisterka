import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {Constants} from '../constants';
import {UserProfile} from '../model/user-profile';
import {UserAccount} from '../model/user-account';

@Injectable()
export class AccountService {
    userAccount: UserAccount;

    constructor(private _httpClient: HttpClient) {
    }

    getUserAccount(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account');
    }

    getUserAllInfo(): Observable<UserAccount> {
        return this._httpClient.get<UserAccount>(Constants.apiRoot + 'account/accountInfo');
    }

    getAllUsers(): Observable<UserProfile[]> {
        return this._httpClient.get<UserProfile[]>(Constants.apiRoot + 'Account/Users');
    }

    createUserProfile(userProfile: UserProfile) {
        return this._httpClient.post(`${Constants.apiRoot}Account/Profile`, userProfile);
    }

    updateUserProfile(userProfile: UserProfile) {
        return this._httpClient.put(`${Constants.apiRoot}Account/Profile/${userProfile.id}`, userProfile);
    }

    finalizeRegister(userAccount: UserAccount) {
        return this._httpClient.post(`${Constants.apiRoot}account/finalize`, userAccount);
    }
}
