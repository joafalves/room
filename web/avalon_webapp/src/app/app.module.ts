import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';
import { ConnectionService } from './services/ConnectionService';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { HubPage } from '../pages/hub/hub';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    HubPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    HubPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {
      provide: ErrorHandler,
      useClass: IonicErrorHandler
    },
    ConnectionService
  ]
})
export class AppModule {}
