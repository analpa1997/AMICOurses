import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
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
export class UserUpdateComponent {

  userUpdated: boolean;
  user: User;
  private service: UserService;



  cancel() {
    window.history.back();
  }

  save() {
    this.service.newUser(this.user).subscribe(
      user => { },
      error => console.error('Error creating new book: ' + error)
    );
    window.history.back();
  }
}
