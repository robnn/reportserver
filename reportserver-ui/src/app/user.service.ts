import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './model/user';
import { Observable, of } from 'rxjs';
import { NotificationService } from './notification.service';
import { Token } from './model/token';
import { LocalStorageService } from './local-storage.service';
import { Router } from '@angular/router';
import { Message } from './model/message';
import { Severity } from './model/severity';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private authToken: string;

  private usersUrl = '/users/';

  private user: User;

  constructor(private http: HttpClient,
    private notificationService: NotificationService,
    private localStorageService: LocalStorageService,
    private router: Router) { }

  register(user: User) {
    this.http.post<User>(this.usersUrl, user).subscribe(() => {
      this.router.navigate(['/login']);
      const successfulLoginMessage = new Message(Severity.INFO, 'SUCCESSFUL_REGISTRATION');
      this.notificationService.addNotification(successfulLoginMessage);
    }, error => {
      this.notificationService.addNotification(error.error);
    });
  }

  login(user: User) {
    this.http.post<Token>(this.usersUrl + 'login', user).subscribe(x => {
      this.authToken = x.token;
      this.localStorageService.storeUserToken(x.token);
      this.router.navigate(['/dashboard']);
      const successfulLoginMessage = new Message(Severity.INFO, 'SUCCESSFUL_LOGIN');
      this.notificationService.addNotification(successfulLoginMessage);
      this.getUserData();
    }, error => {
      this.notificationService.addNotification(error.error);
    });
  }

  getAuthToken(): string {
    if (this.localStorageService.retrieveUserToken() && !this.authToken) {
      this.authToken = this.localStorageService.retrieveUserToken();
    }
    return this.authToken;
  }

  getAuthTokenAsHttpHeader(headers: HttpHeaders): HttpHeaders {
    if (!headers) {
      headers = new HttpHeaders();
    }

    if (!this.getAuthToken()) {
      const notLoggedInMessage = new Message(Severity.ERROR, "NOT_LOGGED_IN");
      this.notificationService.addNotification(notLoggedInMessage);
      return null;
    }

    headers = headers.append('X-Auth-Token', this.getAuthToken());
    return headers;
  }

  getUserForToken(): Observable<User> {
    return this.http.get<User>(this.usersUrl + 'byToken/' + this.getAuthToken(), { headers: this.getAuthTokenAsHttpHeader(null) });
  }

  public getUserData(): User {
    if (!this.user) {
      const observable = this.getUserForToken();
      observable.subscribe(user => {
        this.user = user;
        return user;
      }, error => {
        this.notificationService.addNotification(error.error);
      });
    } else {
      return this.user;
    }
  }

  public invalidateData() {
    this.localStorageService.clearUserToken();
    this.authToken = null;
    this.user = null;
  }

  public getUser(): User {
    if (this.user) {
      return this.user;
    }
    return null;
  }
}
