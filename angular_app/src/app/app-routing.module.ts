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
import {ResourcesComponent} from './components/resources/resources.component';
import {HeroComponent} from './components/hero/hero.component';
import {HeroesComponent} from './components/heroes/heroes.component';
import {MapComponent} from './components/map/map.component';
import {MissionComponent} from './components/mission/mission.component';
import {DungeonsComponent} from './components/dungeons/dungeons.component';


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
            {path: '', redirectTo: 'overview', pathMatch: 'full'},
            {path: 'overview', component: OverviewComponent},
            {path: 'buildings', component: BuildingsComponent},
            {path: 'barracks', component: BarracksComponent},
            {
                path: 'heroes',
                component: HeroesComponent,
                children: [
                    {path: '', redirectTo: 'tavern', pathMatch: 'full'},
                    {path: 'tavern', component: TavernComponent},
                    {path: 'hero/:id', component: HeroComponent},
                    ]
            },
            {path: 'resources', component: ResourcesComponent},
            {path: 'defence', component: DefenceComponent},
            {path: 'population', component: PopulationComponent},
            {path: 'research', component: ResearchComponent},
            {path: 'map', component: MapComponent},
            {path: 'mission', component: MissionComponent},
            {path: 'dungeons', component: DungeonsComponent}
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
