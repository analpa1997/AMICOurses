import { Injectable } from '@angular/core';
import { Course } from '../model/course.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Skill } from '../model/skill.model';
import { Subject } from '../model/subject.model';
import { Observable } from 'rxjs/Observable';
import { Studyitem } from '../model/studyitem.model';
import { Router } from '@angular/router';
import { Practices } from '../model/practices.model';
import { saveAs as importedSaveAs } from "file-saver";
import { User } from '../model/user.model';

const URL = 'https://localhost:8443/api/';

@Injectable()
export class SubjectListService {

  constructor(private http: HttpClient, private router: Router) {

  }

  /* Main */
  getSubject(courseName: string, subjectName: string) {
    const reqUrl = URL + "subjects/" + courseName + "/" + subjectName;
    return this.http.get<Subject>(reqUrl, { withCredentials: true })
  }

  getCourse(courseName: string){
    const reqUrl = URL + "courses/name/" + courseName + "/full";
    return this.http.get<Course>(reqUrl, { withCredentials: true });
  }

  getTeachers(page=0) {
    const reqUrl = URL + "users/all?isStudent=false&page=" + page;
    return this.http.get<any>(reqUrl, { withCredentials: true });
  }

  modifySubject(courseName: string, subject : any): any {
    const reqUrl = URL + "subjects/" + courseName + "/" + subject.internalName;
    return this.http.put<any>(reqUrl, subject, { withCredentials: true });
  }

  createSubject(courseName : string, name : string): any {
    const reqUrl = URL + "subjects/" + courseName + "/subjects?subjectName=" + name;
    return this.http.post<Subject>(reqUrl, {},{ withCredentials: true });
  }

}
