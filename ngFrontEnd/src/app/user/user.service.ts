import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import {User} from '../model/user.model';
import { LoginService } from '../login/login.service';
import { HttpClient } from '@angular/common/http';



const URL = 'https://localhost:8443/api/users';
@Injectable()
export class UserService {

  constructor(private http: Http, private newHttp: HttpClient, private login: LoginService) { }

  getUsers() {
    return this.http.get(URL, { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  getTeachers(page: number) {
    return this.http.get(URL + '/all?page=' + page + '&isStudent=false', { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  getUser(internal: string) {
    return this.http.get(URL + '/' + internal, { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  newUser(user: User) {
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });
    const body = JSON.stringify(user);
      return this.http.post(URL, body, options)
        .map(response => response.json())
        .catch(error => this.handleError(error));
  }
  uploadImage(internalName: string, file: File) {
    let reqUrl;
    const internal = internalName;
    const formData = new FormData();
    formData.append('profileImage', file);
    reqUrl = URL + '/img/' + internal;
      return this.newHttp.put<User>(reqUrl, formData, { withCredentials: true });
  }
  getImageProfile(internal: string) {
    return this.http.get(URL + '/img/' + internal, { withCredentials: true })
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  removeUser(user: User) {

    const headers = new Headers({
      'X-Requested-With': 'XMLHttpRequest'
    });
    const internal = user.internalName;
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.delete(URL + '/' + internal, options)
      .map(rensponse => undefined)
      .catch(error => this.handleError(error));
  }

  updateUser(user: User) {

    const body = JSON.stringify(user);
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    const internal = user.internalName;
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.put(URL + '/' + internal, body, options)
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error (' + error.status + '): ' + error.text());
  }

  checkUsernameAndMail(username: string, userMail: string) {
    const usernameR = 'username=' + username;
    const userMailR = '&userMail=' + userMail;
    const URLRequest = '/request?';

    return this.http.get(URL + URLRequest + usernameR + userMailR)
      .map(response => response.json())
      .catch(error => this.handleError(error));
  }
  checkUser(username: string, password: string, repeatPassword: string, userMail: string, admin: boolean) {
    const errors: boolean[] = [false, false, false]; // All true if there aren't errors
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

    deleteUser(internalName: string) {
      return this.http.delete(URL + '/' + internalName, { withCredentials: true });
    }
}
