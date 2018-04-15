import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UserService } from '../user/user.service';
import {User} from '../model/user.model';

@Component({
  template: `
  <div *ngIf="book">
  <h2>Book "{{book.title}}"</h2>
  <div *ngIf="book.id">
    <label>Id: </label>{{book.id}}
  </div>
  <div>
    <label>Title: </label>
    <input [(ngModel)]="book.title" placeholder="title"/>
  </div>
  <div>
    <label>Abstract: </label>
    <textarea [(ngModel)]="book.description" placeholder="description"></textarea>
  </div>
  <p>
    <button (click)="cancel()">Cancel</button>
    <button (click)="save()">Save</button>
  </p>
  </div>`
})
export class SignupComponent {

  newUser: boolean;
  user: User;

  constructor(
    private _router: Router,
    activatedRoute: ActivatedRoute,
    private service: UserService) {

    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getUser(id).subscribe(
        book => this.user = book,
        error => console.error(error)
      );
      this.newUser = false;
    } else {
      this.user = { username: '', userMail: '' };
      this.newUser = true;
    }
  }

  cancel() {
    window.history.back();
  }

  save() {
    this.service.saveUser(this.user).subscribe(
      user => { },
      error => console.error('Error creating new user: ' + error)
    );
    window.history.back();
  }
}
