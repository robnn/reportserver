import { Component, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { slideInAnimation } from './animations';
import { RouterOutlet } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { NotificationService } from './notification.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Message } from './model/message';
import { SeveritySnackbarComponent } from './severity-snackbar/severity-snackbar.component';
import { UserService } from './user.service';
import { LocalStorageService } from './local-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [slideInAnimation]
})
export class AppComponent implements OnInit {


  private ngUnsubscribe = new Subject();

  constructor(private translateService: TranslateService,
    private notificationService: NotificationService,
    private snackBar: MatSnackBar,
    private userService: UserService,
    private localStorageService: LocalStorageService) {
    this.initializeNotifications();
    translateService.addLangs(['hu', 'en']);
    translateService.setDefaultLang('en');
    if (localStorageService.retrieveLanguage()) {
      translateService.use(localStorageService.retrieveLanguage());
    } else {
      translateService.use('en');
    }
  }

  ngOnInit(): void {
    if(this.userService.getAuthToken() != null){
      this.userService.getUserData();
    }
  }
  onActivate(componentRef) {

  }

  changeToHu() {
    this.localStorageService.storeLanguage('hu');
    this.translateService.use('hu');
  }

  changeToEn() {
    this.localStorageService.storeLanguage('en');
    this.translateService.use('en');
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }

  initializeNotifications(): any {
    this.notificationService
      .getNotifications()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((notif) => {
        notif.forEach(n => {
          this.openSnackbar(n);
        });

      });
  }

  openSnackbar(message: Message) {
    const config = new MatSnackBarConfig();
    // config.duration = 2000;
    config.panelClass = ['severity-snackbar'];
    config.horizontalPosition = 'right';
    config.data = { message };
    const snackBarRef = this.snackBar.openFromComponent(SeveritySnackbarComponent, config);
    snackBarRef.instance.snackBarRef = snackBarRef;
  }
}
