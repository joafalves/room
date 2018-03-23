import { Component } from '@angular/core';
import { ActionSheetController, NavController, ModalController, LoadingController, Events, ToastController } from 'ionic-angular';
import { ConnectionService } from '../../app/services/ConnectionService';
import { GameService } from '../../app/services/GameService';
import { AddRoomPage } from '../add-room/add-room';
import { GamePage } from '../game/game';

@Component({
  selector: 'page-hub',
  templateUrl: 'hub.html'
})
export class HubPage {
  roomData;

  constructor(public modalCtrl: ModalController, public actionSheetCtrl: ActionSheetController, public navCtrl: NavController, public loadingCtrl: LoadingController, private toastCtrl: ToastController, private connectionSrv: ConnectionService, private gameSrv: GameService, private events: Events) {
    //this.refreshRoomsList();
  }

  ionViewWillEnter() {
    this.refreshRoomsList();
  }

  public presentActionSheet() {
    let actionSheet = this.actionSheetCtrl.create({
      title: 'Options',
      buttons: [
        {
          text: 'Create Room',
          handler: this.createRoom.bind(this)
        },{
          text: 'Refresh Rooms List',
          handler: this.refreshRoomsList.bind(this)
        }
      ]
    });
    actionSheet.present();
  }

  public enterRoom(room) {
    this.gameSrv.setActiveRoom(room);
    this.navCtrl.push(GamePage);
  }

  private createRoom() {
    this.navCtrl.push(AddRoomPage);
  }

  private refreshRoomsList() {
    let loading = this.loadingCtrl.create({
      content: "Loading room data...",
      duration: 0
    });
    loading.present();

    this.gameSrv.refreshRoomList().then(message => {
      this.roomData = message;
      loading.dismiss();

    }, err => {
      loading.dismiss();
    });
  }
}
