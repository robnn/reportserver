import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

  public user: User;

  constructor(private http: HttpClient,
    private notificationService: NotificationService,
    private localStorageService: LocalStorageService) { }

  register(user: User) {
    const observable = this.http.post<User>(this.usersUrl, user);
    observable.subscribe(() => {
      const successfulLoginMessage = new Message(Severity.INFO, 'SUCCESSFUL_REGISTRATION');
      this.notificationService.addNotification(successfulLoginMessage);
    }, error => {
      this.notificationService.addNotification(error.error);
    });
    return observable;
  }

  login(user: User) {
    const observable = this.http.post<Token>(this.usersUrl + 'login', user);
    observable.subscribe(x => {
      this.authToken = x.token;
      this.localStorageService.storeUserToken(x.token);
      this.getCurrentUser();
    }, error => {
      this.notificationService.addNotification(error.error);
    });
    return observable;
  }

  loginWithFacebook(accessToken: string): Observable<Token> {
    const observable = this.http.post<Token>(this.usersUrl + 'login/facebook', { token: accessToken });
    observable.subscribe(x => {
      this.authToken = x.token;
      this.localStorageService.storeUserToken(x.token);
    });
    return observable;
  }

  loginWithGoogle(accessToken: string): Observable<Token> {
    const observable = this.http.post<Token>(this.usersUrl + 'login/google', { token: accessToken });
    observable.subscribe(x => {
      this.authToken = x.token;
      this.localStorageService.storeUserToken(x.token);
    });
    return observable;
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
      const notLoggedInMessage = new Message(Severity.ERROR, 'NOT_LOGGED_IN');
      this.notificationService.addNotification(notLoggedInMessage);
      return null;
    }

    headers = headers.append('X-Auth-Token', this.getAuthToken());
    return headers;
  }

  getCurrentUser(): Observable<User> {
    if (!this.user) {
      const observable = this.http.get<User>(this.usersUrl + 'byToken/' +
        this.getAuthToken(), { headers: this.getAuthTokenAsHttpHeader(null) });
      observable.subscribe(resp => {
        this.user = resp;
      });
      return observable;
    }
    return of(this.user);
  }

  hasRole(role: string): boolean {
    if (!this.user) {
      return false;
    } else {
      return this.user.roles.map(x => x.roleCode).includes(role);
    }
  }

  public invalidateData() {
    this.localStorageService.clearUserToken();
    this.authToken = null;
    this.user = null;
  }
}