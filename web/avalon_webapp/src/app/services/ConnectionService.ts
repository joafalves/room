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
      this.stompClient.subscribe("/user/queue/hello", this.onHelloReply.bind(this));
      this.stompClient.subscribe("/user/queue/room/create", this.onRoomCreateReply.bind(this));
      this.stompClient.subscribe("/user/queue/room/list", this.onRoomListReply.bind(this));
      this.stompClient.subscribe("/user/queue/room/info", this.onRoomInfoReply.bind(this));
    });
  }

  public subscribe(topic:String, handler) {
    this.stompClient.subscribe(topic, (message) => {
      if (handler != null) {
        handler(this.getMessageData(message));
      }
    });
  }

  public unsubscribe(topic:String) {
    this.stompClient.unsubscribe(topic);
  }

  public sendHello(firstName:String, lastName:String) {
    let message = JSON.stringify({firstName: firstName, lastName: lastName});
    this.stompClient.send("/app/hello", {}, message);
  }

  public createRoom(roomName:String) {
    this.stompClient.send("/app/room/create", {}, JSON.stringify({roomName: roomName}));
  }

  public listRooms() {
    this.stompClient.send("/app/room/list", {});
  }

  public getRoleInfo(roomName:String) {
    this.stompClient.send("/app/room/role-info", {}, JSON.stringify({roomName: roomName}))
  }

  public getRoomInfo(roomName:String) {
    this.stompClient.send("/app/room/info", {}, JSON.stringify({roomName: roomName}))
  }

  public restartGame(roomName:String) {
    this.stompClient.send("/app/room/restart", {}, JSON.stringify({roomName: roomName}));
  }

  public joinRoom(roomName:String) {
    this.stompClient.send("/app/room/join", {}, JSON.stringify({roomName: roomName}));
  }

  public leaveRoom(roomName:String) {
    this.stompClient.send("/app/room/leave", {}, JSON.stringify({roomName: roomName}));
  }

  private getMessageData(message) {
    if (message && message.body) {
      return JSON.parse(message.body);
    }

    return null;
  }

  private onHelloReply(message) {
    let messageData = this.getMessageData(message);
    this.events.publish("greeting", messageData && messageData.success);
  }

  private onRoomCreateReply(message) {
    this.events.publish("onRoomCreateReply", this.getMessageData(message));
  }

  private onRoomListReply(message) {
    this.events.publish("onRoomListReply", this.getMessageData(message));
  }

  private onRoomInfoReply(message) {
    this.events.publish("onRoomInfoReply", this.getMessageData(message));
  }

}
