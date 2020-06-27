import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SigninRedirectCallbackComponent} from './home/signin-redirect-callback.component';
import {SignoutRedirectCallbackComponent} from './home/signout-redirect-callback.component';
import {UnauthorizedComponent} from './home/unauthorized.component';
import {TestComponent} from './test/test.component';
import {CreateAccountComponent} from './components/create-account/create-account.component';


const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'signin-callback', component: SigninRedirectCallbackComponent},
    {path: 'create-account', component: CreateAccountComponent},
    {path: 'signout-callback', component: SignoutRedirectCallbackComponent},
    {path: 'public', component: TestComponent},
    {path: 'private', component: UnauthorizedComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
