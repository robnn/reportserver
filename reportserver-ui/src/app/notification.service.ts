import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Message } from './model/message';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notifications = new Subject<Message[]>();

  constructor() { }

  public addNotification = (notification: Message): void => {
    this.notifications.next(new Array(notification));
  }

  public getNotifications = () =>
      this.notifications.asObservable()

}
