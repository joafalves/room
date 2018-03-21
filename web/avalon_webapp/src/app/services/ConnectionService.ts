import { Events } from 'ionic-angular';
import { Injectable } from '@angular/core';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

@Injectable()
export class ConnectionService {
  private serverUrl = 'http://localhost:8080/avalon'
  private stompClient;

  constructor(private events: Events) {
  }

  public init() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame) => {
      this.stompClient.subscribe("/topic/greetings", this.onGreetingNotify.bind(this));
      this.stompClient.subscribe("/topic/rooms", this.onRoomNotify.bind(this));
    });
  }

  public sendHello(firstName:String, lastName:String) {
    let message = JSON.stringify({firstName: firstName, lastName: lastName});
    this.stompClient.send("/app/hello", {}, message);
  }

  public createRoom(roomName:String, playerCount:Number) {

  }

  public listRooms() {

  }

  public joinRoom() {

  }

  private getMessageData(message) {
    if (message && message.body) {
      return JSON.parse(message.body);
    }

    return null;
  }

  private onGreetingNotify(message) {
    let messageData = this.getMessageData(message);
    this.events.publish("greeting", messageData && messageData.success);
  }

  private onRoomNotify(message) {

  }

}
