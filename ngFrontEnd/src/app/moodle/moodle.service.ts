import { Injectable } from '@angular/core';
import {Course } from '../model/course.model';
import { HttpClient } from '@angular/common/http';
import {Skill} from '../model/skill.model';
import {Subject} from '../model/subject.model';
import { Observable } from 'rxjs/Observable';

const URL = 'https://localhost:8443/api/';

@Injectable()
export class MoodleService {
  constructor(private http: HttpClient) {}

  getSubject(courseName : string, subjectName : string) {
    let reqUrl = URL + "subjects/" + courseName + "/" + subjectName ;
    return this.http.get<Subject>(reqUrl, { withCredentials: true })
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw(error.status);
  }
}
