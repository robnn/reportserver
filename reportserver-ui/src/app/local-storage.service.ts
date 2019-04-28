import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'angular-webstorage-service';


const USER_TOKEN_STORAGE_KEY = 'user_token';
const LANGUAGE_STORAGE_KEY = 'language';

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

  public storeLanguage(language: string) {
    this.storage.set(LANGUAGE_STORAGE_KEY, language);
  }

  public retrieveLanguage(): string {
    return this.storage.get(LANGUAGE_STORAGE_KEY);
  }

  public clearLanguage() {
    this.storage.remove(LANGUAGE_STORAGE_KEY);
  }

}
