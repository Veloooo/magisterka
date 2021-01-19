import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {UserAccount} from '../../model/user-account';
import {AccountService} from '../../core/account.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-create-account',
    templateUrl: './create-account.component.html',
    styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit {

    newUser: UserAccount;
    userFraction = new FormGroup({
        fraction: new FormControl(''),
    });

    constructor(private accountService: AccountService,
                private _router: Router) {
    }

    ngOnInit() {
    }

    onSubmit() {
        this.newUser = new UserAccount();
        this.newUser.fraction = this.userFraction.value['fraction'];
        console.log(this.accountService.finalizeRegister(this.newUser).subscribe(data => this._router.navigate(['main-view'], {replaceUrl: true})));
    }

}
