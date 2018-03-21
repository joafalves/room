import { Component } from '@angular/core';
import { LoadingController, Loading, NavController, Events, Toast, ToastController } from 'ionic-angular';
import { ConnectionService } from '../../app/services/ConnectionService';
import { HubPage } from '../hub/hub';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  firstName:String;
  lastName:String;
  loading:Loading;

  constructor(public navCtrl: NavController, public connectionSrv: ConnectionService, public loadingCtrl: LoadingController, private toastCtrl: ToastController, private events: Events) {
    this.firstName = "João";
    this.lastName = "Alves";

    this.events.subscribe("greeting", (success) => {
      this.loading.dismiss();

      if (!success) {
        return;
      }

      this.navCtrl.setRoot(HubPage);
    });
  }

  public continue() {
    if (this.firstName.trim().length <= 1 || this.lastName.trim().length <= 1) {
      let toast = this.toastCtrl.create({
        message: 'Please fill the details before proceeding..',
        duration: 2000,
        position: 'middle'
      });
      toast.present();
      return;
    }

    this.presentLoading();
    this.connectionSrv.sendHello(this.firstName, this.lastName);
  }

  private presentLoading() {
    this.loading = this.loadingCtrl.create({
      content: "Please wait...",
      duration: 0
    });
    this.loading.present();
  }

}
