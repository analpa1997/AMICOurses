import { Injectable } from '@angular/core';
import {Course } from '../model/course.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

const URL = 'https://localhost:8443/api/courses/';

@Injectable()

export class CourseService {
  constructor(private http: HttpClient) {}

   oneCourse(id: number | string) {
    return this.http.get<Course>(URL + id);
  }
  skills(id: number | string) {
    return this.http.get<Course>(URL + id + '/skills');
  }

  subjects(id: number | string) {
    return this.http.get<Course>(URL + id + '/subjects');
  }

  getCourses(page : number, order : string) {
    let pageReq = "";
    let pageOrd = "";
    pageReq ="page=" + ((page) ? page : 0);
    pageOrd = "sort=" + ((order) ? order : "courseID");
    let reqUrl = URL + "?" + pageReq + "&" + pageOrd + "/" ;
    return this.http.get(URL, { withCredentials: true })
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error (' + error.status + '): ' + error.text());
  }
}
