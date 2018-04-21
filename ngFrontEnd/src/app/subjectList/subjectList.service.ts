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

const URL = 'https://localhost:8443/api/';

@Injectable()
export class SubjectListService {


  constructor(private http: HttpClient, private router: Router) { }

  /* Main */
  getSubject(courseName: string, subjectName: string) {
    const reqUrl = URL + "subjects/" + courseName + "/" + subjectName;
    return this.http.get<Subject>(reqUrl, { withCredentials: true })
  }

  getCourse(courseName: string){
    const reqUrl = URL + "courses/name/" + courseName + "/full";
    return this.http.get<Course>(reqUrl, { withCredentials: true });
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
