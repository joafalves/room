import { Component } from '@angular/core';
import {IonicPage, NavController, Toast, Events, NavParams, ViewController, ToastController} from 'ionic-angular';
import { ConnectionService } from '../../app/services/ConnectionService';
import {HubPage} from "../hub/hub";

/**
 * Generated class for the AddRoomPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-add-room',
  templateUrl: 'add-room.html',
})
export class AddRoomPage {
  roomName:String;

  constructor(public connectionSrv: ConnectionService, public viewCtrl: ViewController, public navCtrl: NavController, public navParams: NavParams, private toastCtrl: ToastController, private events: Events) {
    this.events.subscribe("onRoomCreateReply", (response) => {
      if (response.success) {
        let toast = this.toastCtrl.create({
          message: 'Room created successfully',
          duration: 2000,
          position: 'middle'
        });
        toast.present();
        this.viewCtrl.dismiss();
        return;
      }

      alert("Could not create room. " + response.message);
    });
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddRoomPage');
  }

  public continue() {
    if (this.roomName.trim().length < 4) {
      alert("The room name must have more than 3 characters");
      return;
    }

    this.connectionSrv.createRoom(this.roomName);
  }

  dismiss() {
    this.navCtrl.pop();
  }

}
