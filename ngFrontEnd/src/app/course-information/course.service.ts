import { Injectable } from '@angular/core';
import {Course } from '../model/course.model';
import { HttpClient } from '@angular/common/http';
import {Skill} from '../model/skill.model';
import {Subject} from '../model/subject.model';
import { Observable } from 'rxjs/Observable';
import {Studyitem} from '../model/studyitem.model';

const URL = 'https://localhost:8443/api/courses/';

@Injectable()
export class CourseService {
  constructor(private http: HttpClient) {}

  oneCourse(id: number | string) {
    return this.http.get<Course>(URL  + 'id/' + id + '/', { withCredentials: true });
  }
  skills(id: number | string) {
    return this.http.get<Skill[]>(URL + 'id/' + id + '/skills/', { withCredentials: true });
  }

  subjects(id: number | string) {
    return this.http.get<Subject[]>(URL + 'id/' + id + '/subjects/', { withCredentials: true });
  }

  addCourseToUser(id: number | string) {
    return this.http.put(URL + 'id/' + id + '/add', null, {withCredentials: true});
  }

  getTypes(){
    return this.http.get<string[]>(URL + 'types/', { withCredentials: true });
  }

  getCourses(page : number, name : string, type : string, order : string) {
    let pageReq = "";
    let pageOrd = "";
    let nameCourses = "";
    let typeCourses = "";
    pageReq ="page=" + ((page) ? page : 0);
    pageOrd = "sort=" + ((order) ? order : "courseID");
    nameCourses = "name=" + ((name) ? name : "");
    typeCourses = "type=" + ((type) ? type : "all");
    let reqUrl = URL + "?" + pageReq + "&" + nameCourses + "&" + typeCourses + "&" + pageOrd;

    return this.http.get(reqUrl, { withCredentials: true })
  }

  deleteCourse(internalName: string) {
    let reqUrl = URL + 'name/' + internalName+'/';
    return this.http.delete(reqUrl, {withCredentials: true});
  }

  modifyCourse(course: Course) {
    return this.http.put(URL, course, { withCredentials: true });
  }

  createCourse(course: Course){
    return this.http.post(URL, course, { withCredentials: true });
  }


  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error (' + error.status + '): ' + error.text());
  }
}
