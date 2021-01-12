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
import {OverviewComponent} from './components/overview/overview.component';
import {BuildingsComponent} from './components/buildings/buildings.component';
import {BarracksComponent} from './components/barracks/barracks.component';
import {TavernComponent} from './components/tavern/tavern.component';
import {DefenceComponent} from './components/defence/defence.component';
import {PopulationComponent} from './components/population/population.component';
import {ResearchComponent} from './components/research/research.component';


const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'signin-callback', component: SigninRedirectCallbackComponent},
    {path: 'create-account', component: CreateAccountComponent, canActivate: [GameRouteGuard]},
    {path: 'signout-callback', component: SignoutRedirectCallbackComponent},
    {path: 'public', component: TestComponent},
    {
        path: 'main-view',
        component: MainViewComponent,
        children: [
            {path: '', redirectTo: 'overview', pathMatch: "full"},
            {path: 'overview', component: OverviewComponent},
            {path: 'buildings', component: BuildingsComponent},
            {path: 'barracks', component: BarracksComponent},
            {path: 'tavern', component: TavernComponent},
            {path: 'defence', component: DefenceComponent},
            {path: 'population', component: PopulationComponent},
            {path: 'research', component: ResearchComponent}
        ]
    },
    {path: 'private', component: UnauthorizedComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
