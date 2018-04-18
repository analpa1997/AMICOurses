import { Injectable } from '@angular/core';
import {Course } from '../model/course.model';
import { HttpClient } from '@angular/common/http';
import {Skill} from '../model/skill.model';
import {Subject} from '../model/subject.model';
import { Observable } from 'rxjs/Observable';
import { Studyitem } from '../model/studyitem.model';

const URL = 'https://localhost:8443/api/';

@Injectable()
export class MoodleService {
  constructor(private http: HttpClient) {}

  getSubject(courseName : string, subjectName : string) {
    let reqUrl = URL + "subjects/" + courseName + "/" + subjectName ;
    return this.http.get<Subject>(reqUrl, { withCredentials: true })
  }

  getStudyItemsFromModule(courseName : string, subjectName : string, module : number) {
    let reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/studyItem/module/" + module;
    return this.http.get(reqUrl, { withCredentials: true })
  }

  getStudyItemFile(courseName : string, subjectName : string, studyItemId : number) {
    let reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/studyItem/file/" + studyItemId;
    console.log(reqUrl);
    return this.http.get(reqUrl, { withCredentials: true, responseType : 'blob' });
  }

  modifyStudyItem (courseName : string, subjectName : string, studyItem : Studyitem) {
    let type = studyItem.isPractice ? "practice" : "studyItem";
    let reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/" + studyItem.studyItemID;

    return this.http.put<Studyitem>(reqUrl, studyItem,  { withCredentials: true });
  }


  deleteStudyItem (courseName : string, subjectName : string, studyItem : Studyitem) {
    let type = studyItem.isPractice ? "practice" : "studyItem";
    let reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/" + studyItem.studyItemID;

    return this.http.delete<Studyitem>(reqUrl, { withCredentials: true });
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw(error.status);
  }
}
