import { Injectable } from '@angular/core';
import {Course } from '../model/course.model';
import { HttpClient } from '@angular/common/http';

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
}
