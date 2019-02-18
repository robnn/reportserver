import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'angular-webstorage-service';


const USER_TOKEN_STORAGE_KEY = 'user_token';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService) { }

  public storeUserToken(token: string) {
    this.storage.set(USER_TOKEN_STORAGE_KEY, token);
  }

  public retrieveUserToken(): string {
    return this.storage.get(USER_TOKEN_STORAGE_KEY);
  }

  public clearUserToken() {
    this.storage.remove(USER_TOKEN_STORAGE_KEY);
  }
}
