import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';
import { ConnectionService } from './services/ConnectionService';
import { GameService } from './services/GameService';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { HubPage } from '../pages/hub/hub';
import { AddRoomPage } from '../pages/add-room/add-room';
import { GamePage } from '../pages/game/game';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    HubPage,
    AddRoomPage,
    GamePage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    HubPage,
    AddRoomPage,
    GamePage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {
      provide: ErrorHandler,
      useClass: IonicErrorHandler
    },
    ConnectionService,
    GameService
  ]
})
export class AppModule {}
