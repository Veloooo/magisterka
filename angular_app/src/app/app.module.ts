import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {HomeComponent} from './home/home.component';
import {SigninRedirectCallbackComponent} from './home/signin-redirect-callback.component';
import {SignoutRedirectCallbackComponent} from './home/signout-redirect-callback.component';
import {UnauthorizedComponent} from './home/unauthorized.component';
import {TestComponent} from './test/test.component';
import {CreateAccountComponent} from './components/create-account/create-account.component';
import { MainViewComponent } from './components/main-view/main-view.component';
import { OverviewComponent } from './components/overview/overview.component';
import { BuildingsComponent } from './components/buildings/buildings.component';
import { BarracksComponent } from './components/barracks/barracks.component';
import { TavernComponent } from './components/tavern/tavern.component';
import { DefenceComponent } from './components/defence/defence.component';
import { PopulationComponent } from './components/population/population.component';
import { ResearchComponent } from './components/research/research.component';
import { ResourcesComponent } from './components/resources/resources.component';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        SigninRedirectCallbackComponent,
        SignoutRedirectCallbackComponent,
        UnauthorizedComponent,
        TestComponent,
        CreateAccountComponent,
        MainViewComponent,
        OverviewComponent,
        BuildingsComponent,
        BarracksComponent,
        TavernComponent,
        DefenceComponent,
        PopulationComponent,
        ResearchComponent,
        ResourcesComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatToolbarModule,
        MatDialogModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        CoreModule,
        AppRoutingModule,
        ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AppComponent],
    entryComponents: []
})
export class AppModule {
}
