import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SigninRedirectCallbackComponent} from './home/signin-redirect-callback.component';
import {SignoutRedirectCallbackComponent} from './home/signout-redirect-callback.component';
import {UnauthorizedComponent} from './home/unauthorized.component';
import {TestComponent} from './test/test.component';
import {CreateAccountComponent} from './components/create-account/create-account.component';
import {GameRouteGuard} from './core/game-route-guard';
import {MainViewComponent} from './components/main-view/main-view.component';


const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'signin-callback', component: SigninRedirectCallbackComponent},
    {path: 'create-account', component: CreateAccountComponent, canActivate: [GameRouteGuard]},
    {path: 'signout-callback', component: SignoutRedirectCallbackComponent},
    {path: 'public', component: TestComponent},
    {path: 'main-view', component: MainViewComponent},
    {path: 'private', component: UnauthorizedComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
