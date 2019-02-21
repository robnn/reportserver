import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user: User;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = new User();
  }

  loginSubmit() {
    this.userService.login(this.user);
  }
}
