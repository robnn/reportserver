import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user: User;
  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit() {
    if (this.userService.getAuthToken()) {
      this.router.navigate(['/dashboard']);
    }
    this.user = new User();
  }

  loginSubmit() {
    this.userService.login(this.user).subscribe(() => {
      this.router.navigate(['/dashboard']);
    });
  }
}
