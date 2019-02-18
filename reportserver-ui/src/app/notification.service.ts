import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Message } from './model/message';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notifications = new Subject<Message[]>();

  constructor() { }

  public addNotification = (nofitication: Message): void => {
    console.log("Added message: " + nofitication.message);
    this.notifications.next(new Array(nofitication));
  }

  public getNotifications = () => 
      this.notifications.asObservable()
  
}
