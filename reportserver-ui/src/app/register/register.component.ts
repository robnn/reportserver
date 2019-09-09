import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NotificationService} from '../notification.service';
import {UserService} from '../user.service';
import {User} from '../model/user';
import {Message} from '../model/message';
import {Severity} from '../model/severity';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public user: User = new User();
  public secondPassword: string;

  constructor(private notificationService: NotificationService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {
  }

  registerSubmit() {
    if (this.user.password !== this.secondPassword) {
      this.notificationService.addNotification(new Message(Severity.ERROR, 'NOT_EQUAL_PASSWORDS'));
      return;
    }
    if (!this.user.emailAddress) {
      this.notificationService.addNotification(new Message(Severity.ERROR, 'EMPTY_EMAIL'));
      return;
    }
    if (!this.user.password) {
      this.notificationService.addNotification(new Message(Severity.ERROR, 'EMPTY_PASSWORD'));
      return;
    }
    if (!this.user.username) {
      this.notificationService.addNotification(new Message(Severity.ERROR, 'EMPTY_USERNAME'));
      return;
    }
    this.userService.register(this.user).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

}
