import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import {User} from '../model/user.model';



const URL = 'https://localhost:8443/api/users';
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

    if (!user.userID) {
      return this.http.post(URL, body, options)
        .map(response => response.json())
        .catch(error => this.handleError(error));
    } else {
      return this.http.put(URL + user.userID, body, options)
        .map(response => response.json())
        .catch(error => this.handleError(error));
    }
  }

  removeUser(user: User) {

    const headers = new Headers({
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.delete(URL + user.userID, options)
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

    return this.http.put(URL + user.userID, body, options)
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error (' + error.status + '): ' + error.text());
  }

  checkUser(username: string, password: string, repeatPassword: string, userMail: string, admin: boolean) {
    const usernameR = 'username=' + username;
    const userMailR = '&userMail=' + userMail;
    const URLRequest = '/request/?';
    const errors: boolean[] = [false, false, false, false, false]; // All true if there aren't errors

    this.http.get(URL + URLRequest + usernameR + userMailR).subscribe(
      response => {
        const res = response.json();
        errors[3] = res[0];
        errors[4] = res[1];
      },
        error => console.error(error)
      );

    function passwordMatch() {
      return (password === repeatPassword && password.length > 7 && password.length < 15);
    }
    function correctName() {
      return (username.length > 4 && username.length < 16);
    }
    function isValidEmailAddress() {
      return true;
    }
    errors[0] = passwordMatch();
    errors[1] = correctName();
    errors[2] = isValidEmailAddress();

    return errors;
    }
}
