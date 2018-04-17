import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from './user.service';
import { LoginService } from '../login/login.service';
import {User} from '../model/user.model';

@Component({
  template: `
  <div *ngIf="book">
  <h2>Book "{{book.title}}"</h2>
  <div>
    <p>{{book.description}}</p>
  </div>
  <p>
    <button *ngIf="loginService.isLogged && loginService.isAdmin" (click)="removeBook()">Remove</button>
    <button *ngIf="loginService.isLogged" (click)="editBook()">Edit</button>
    <br>
    <button (click)="gotoBooks()">All Books</button>
  </p>
  </div>`
})
export class UserComponent {

  user: User;

  constructor(private router: Router, activatedRoute: ActivatedRoute, public service: UserService,
              public loginService: LoginService) {

    const id = activatedRoute.snapshot.params['id'];
    service.getUser(id).subscribe(
      user => this.user = user,
      error => console.error(error)
    );
  }

  removeUser() {
    const okResponse = window.confirm('Do you want to remove this user?');
    if (okResponse) {
      this.service.removeUser(this.user).subscribe(
        _ => this.router.navigate(['/users']),
        error => console.error(error)
      );
    }
  }

  updateUser() {
    this.router.navigate(['/user/update', this.user.userID]);
  }

  gotoUsers() {
    this.router.navigate(['/users']);
  }
}
