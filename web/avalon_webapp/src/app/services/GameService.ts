import { Events } from 'ionic-angular';
import { Injectable } from '@angular/core';
import { ConnectionService } from "./ConnectionService";

@Injectable()
export class GameService {
  private rooms:Array<any>;
  private activeRoom:any;
  private resolve:any;
  private resolveInfo:any;

  constructor(private events: Events, private connectionSrv: ConnectionService) {
    this.resolve = null;
    this.events.subscribe("onRoomListReply", (message) => {
      this.rooms = message;
      this.resolve(message);
      this.resolve = null;
    }, () => {
      this.resolve = null;
    });
    this.events.subscribe("onRoomInfoReply", (message) => {
      this.rooms = message;
      this.resolveInfo(message);
      this.resolveInfo = null;
    }, () => {
      this.resolveInfo = null;
    });
  }

  public refreshRoomList() {
    if (this.resolve != null) {
      // refresh already occurring..
      return Promise.reject("ongoing request already in place");
    }

    // TODO: add timeout..
    let p = new Promise(resolve => {
      this.resolve = resolve;
    });

    this.connectionSrv.listRooms();

    return p;
  }

  public refreshRoomInfo(roomName:String) {
    if (this.resolveInfo != null) {
      // refresh already occurring..
      return Promise.reject("ongoing request already in place");
    }

    // TODO: add timeout..
    let p = new Promise(resolve => {
      this.resolveInfo = resolve;
    });

    this.connectionSrv.getRoomInfo(roomName);

    return p;
  }

  public getRoomList() {
    return this.rooms;
  }

  public setActiveRoom(room) {
    this.activeRoom = room;
  }

  public getActiveRoom() {
    return this.activeRoom;
  }
}
