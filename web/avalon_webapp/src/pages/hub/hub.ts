import { Component } from '@angular/core';
import { ActionSheetController, NavController, LoadingController, Events, ToastController } from 'ionic-angular';
import { ConnectionService } from '../../app/services/ConnectionService';

@Component({
  selector: 'page-hub',
  templateUrl: 'hub.html'
})
export class HubPage {

  constructor(public actionSheetCtrl: ActionSheetController, public navCtrl: NavController, public loadingCtrl: LoadingController, private toastCtrl: ToastController, private connectionSrv: ConnectionService, private events: Events) {

  }

  presentActionSheet() {
    let actionSheet = this.actionSheetCtrl.create({
      title: 'Options',
      buttons: [
        {
          text: 'Create Room',
          handler: this.createRoom.bind(this)
        },{
          text: 'Refresh Rooms List',
          handler: this.refreshRoomsList.bind(this)
        },{
          text: 'Cancel',
          role: 'cancel'
        }
      ]
    });
    actionSheet.present();
  }

  private createRoom() {

  }

  private refreshRoomsList() {

  }
}
