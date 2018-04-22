import { Injectable, OnInit } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import 'rxjs/Rx';
import { User } from '../model/user.model';
import { Router } from '@angular/router';

const URL = 'https://localhost:8443/api';


@Injectable()
export class LoginService {

    isLogged = false;
    isAdmin = false;
    isStudent;
    user: User;

    constructor(private http: Http, private router: Router) {
        this.reqIsLogged();
    }

    reqIsLogged() {

        const headers = new Headers({
            'X-Requested-With': 'XMLHttpRequest'
        });

        const options = new RequestOptions({ withCredentials: true, headers });

        this.http.get(URL + '/logIn', options).subscribe(
            response => this.processLogInResponse(response),
            error => {
                if (error.status !== 401) {
                    console.error('Error when asking if logged: ' +
                        JSON.stringify(error));
                }
            }
        );
    }

    private processLogInResponse(response) {
        this.isLogged = true;
        this.user = response.json();
        this.isAdmin = this.user.roles.indexOf('ROLE_ADMIN') !== -1;
        this.isStudent = this.user.student;
    }

    logIn(user: string, pass: string) {

        const userPass = user + ':' + pass;

        const headers = new Headers({
            'Authorization': 'Basic ' + utf8_to_b64(userPass),
            'X-Requested-With': 'XMLHttpRequest'
        });

        const options = new RequestOptions({ withCredentials: true, headers });

        return this.http.get(URL + '/logIn', options).map(
            response => {
                this.processLogInResponse(response);
                return this.user;
            },
            error => {
                if (error.status == 401) {
                    this.router.navigate(['/login']); //Forbidden
                }
            },
        );
    }

    logOut() {

        return this.http.get(URL + '/logOut', { withCredentials: true }).map(
            response => {
                this.isLogged = false;
                this.isAdmin = false;
                return response;
            }
        );
    }

    /* Error handling */
    errorHandler(error: any) {
        if (error.status == 401) {
            this.router.navigate(['/login']); //Forbidden
        }

        if (error.status == 500) {
            this.router.navigate(['/error404']); //Must be a 500 error
        }

        this.router.navigate(['/error404']);
    }
}

function utf8_to_b64(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function (match, p1) {
        return String.fromCharCode(<any>'0x' + p1);
    }));
}
