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
export class MoodleService {

  constructor(private http: HttpClient, private router: Router) { }

  /* Main */
  getSubject(courseName: string, subjectName: string) {
    const reqUrl = URL + "subjects/" + courseName + "/" + subjectName;
    return this.http.get<Subject>(reqUrl, { withCredentials: true })
  }

  /* Contents tab */
  getStudyItemsFromModule(courseName: string, subjectName: string, module: number) {
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/studyItem/module/" + module;
    return this.http.get(reqUrl, { withCredentials: true })
  }

  downloadFile(courseName: string, subjectName: string, studyItem: Studyitem, practiceSubmission?: Practices) {

    let reqUrl, fileName;
    if (!practiceSubmission) {
      let type = studyItem.isPractice ? "practice" : "studyItem";
      reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/file/" + studyItem.studyItemID;
      fileName = studyItem.originalName;
    } else {
      reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + studyItem.studyItemID + "/file/" + practiceSubmission.practiceID;
      fileName = practiceSubmission.originalName;
    }
    return this.http.get(reqUrl, { withCredentials: true, responseType: 'blob' }).subscribe(
      res => {
        importedSaveAs(res, fileName);
      },
      error => console.log
    );
  }

  modifyStudyItem(courseName: string, subjectName: string, studyItem: Studyitem) {
    const type = studyItem.isPractice ? "practice" : "studyItem";
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/" + studyItem.studyItemID;

    return this.http.put<Studyitem>(reqUrl, studyItem, { withCredentials: true });
  }


  deleteStudyItem(courseName: string, subjectName: string, studyItem: Studyitem) {
    const type = studyItem.isPractice ? "practice" : "studyItem";
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/" + studyItem.studyItemID;

    return this.http.delete<Studyitem>(reqUrl, { withCredentials: true });
  }

  createStudyItem(courseName: string, subjectName: string, module: number, studyItem: Studyitem) {

    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/studyItem/module/" + module;
    return this.http.post<Studyitem>(reqUrl, studyItem, { withCredentials: true })
  }

  uploadFile(courseName: string, subjectName: string, studyItem: Studyitem, file: File, practiceSubmission?: Practices, update?: boolean): Observable<any> {
    let reqUrl;
    let formData = new FormData();
    formData.append('itemFile', file)
    if (!practiceSubmission) {
      const type = studyItem.isPractice ? "practice" : "studyItem";
      reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/" + type + "/file/" + studyItem.studyItemID;
      return this.http.post<Studyitem>(reqUrl, formData, { withCredentials: true });
    } else {
      reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + studyItem.studyItemID + "/file/" + practiceSubmission.practiceID;
      console.log(update);
      if (update) {
        return this.http.put<Practices>(reqUrl, formData, { withCredentials: true });
      }
      return this.http.post<Practices>(reqUrl, formData, { withCredentials: true });
    }
  }

  /* Practices tab */
  getStudyItemsPractices(courseName: string, subjectName: string) {
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/practice/all";
    return this.http.get(reqUrl, { withCredentials: true })
  }

  getPracticesResponse(courseName: string, subjectName: string, practice: Studyitem) {
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + practice.studyItemID;
    return this.http.get(reqUrl, { withCredentials: true });
  }

  modifyPracticeSubmission(courseName: string, subjectName: string, practice: Studyitem, practiceSubmission: Practices) {
    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + practice.studyItemID + "/" + practiceSubmission.practiceID;
    console.log("HOLAAAAA");
    console.log(practiceSubmission);
    return this.http.put<Practices>(reqUrl, practiceSubmission, { withCredentials: true });
  }

  createPractice(courseName: string, subjectName: string, practice: Studyitem) {

    const reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/practice";
    return this.http.post<Studyitem>(reqUrl, practice, { withCredentials: true })
  }

  savePracticeSubmission(courseName: string, subjectName: string, practice: Studyitem, practiceSubmission: Practices) {
    let reqUrl;

    if (practiceSubmission.presented) {
      reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + practice.studyItemID + "/" + practiceSubmission.practiceID;
      return this.http.put<Practices>(reqUrl, practiceSubmission, { withCredentials: true });
    }

    reqUrl = URL + "moodle/" + courseName + "/" + subjectName + "/submissions/" + practice.studyItemID;
    return this.http.post<Practices>(reqUrl, practiceSubmission, { withCredentials: true });
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
