import { Component } from '@angular/core';
import { IonicPage, ActionSheetController, NavController, ModalController, LoadingController, Events, ToastController } from 'ionic-angular';
import { ConnectionService } from '../../app/services/ConnectionService';
import { GameService } from '../../app/services/GameService';

/**
 * Generated class for the GamePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-game',
  templateUrl: 'game.html',
})
export class GamePage {
  roomData;
  roleDetails = null;

  constructor(public modalCtrl: ModalController, public actionSheetCtrl: ActionSheetController, public navCtrl: NavController, public loadingCtrl: LoadingController, private toastCtrl: ToastController, private connectionSrv: ConnectionService, private gameSrv: GameService, private events: Events) {
    this.roomData = this.gameSrv.getActiveRoom();
  }

  ionViewWillEnter() {
    this.connectionSrv.getRoleInfo(this.roomData.name);
  }

  ionViewDidLoad() {
    this.connectionSrv.subscribe("/topic/room/updated/" + this.roomData.name, (players) => {
      this.roomData.players = players;
      this.gameSrv.refreshRoomInfo(this.roomData.name).then(
        (roomData) => {
          this.roomData = roomData;
      }, () => {});
    });
    this.connectionSrv.subscribe("/topic/room/restarted/" + this.roomData.name, (roomData) => {
      this.roomData = roomData;
      this.connectionSrv.getRoleInfo(this.roomData.name);
    });
    this.connectionSrv.subscribe("/user/queue/room/role-info", (roleDetails) => {
      this.roleDetails = roleDetails;
    });
  }

  ionViewWillLeave() {
    this.connectionSrv.unsubscribe("/topic/room/updated/" + this.roomData.name);
    this.connectionSrv.unsubscribe("/topic/room/restarted/" + this.roomData.name);
    this.connectionSrv.unsubscribe("/user/queue/room/role-info" + this.roomData.name);
  }

  activePlayersCount() {
    let count = 0;
    this.roomData.players.forEach((player) => {
      count += player.characterId != null ? 1 : 0;
    });
    return count;
  }

  restartGame() {
    this.connectionSrv.restartGame(this.roomData.name);
  }

  joinRoom() {
    this.connectionSrv.joinRoom(this.roomData.name);
  }

  leaveRoom() {
    this.connectionSrv.leaveRoom(this.roomData.name);
  }

}
