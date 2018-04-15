import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import {User} from '../model/user.model';

export class User  {
  id?: number;
  username: string;
  UserMail: string;
  password: string;
}

const URL = 'https://localhost:8443/api/users/';
@Injectable()
export class UserService {

  constructor(private http: Http) { }

  getUsers() {
    return this.http.get(URL, { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  getUser(id: number) {
    return this.http.get(URL + id, { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  newUser(user: User) {

    const body = JSON.stringify(user);
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });

    if (!user.id) {
      return this.http.post(URL, body, options)
        .map(response => response.json())
        .catch(error => this.handleError(error));
    } else {
      return this.http.put(URL + user.id, body, options)
        .map(response => response.json())
        .catch(error => this.handleError(error));
    }
  }

  removeUser(user: User) {

    const headers = new Headers({
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.delete(URL + user.id, options)
      .map(response => response.undefined)
      .catch(error => this.handleError(error));
  }

  updateUser(user: User) {

    const body = JSON.stringify(user);
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.put(URL + user.id, body, options)
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error (' + error.status + '): ' + error.text());
  }
}
