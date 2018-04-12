import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { environment } from '../../environments/environment';


@Component({
    selector : 'course-index-component',
  templateUrl: './courseIndex.component.html',
  styleUrls : ['../../assets/css/style.css']
})



export class CourseIndexComponent {

    @Input()
  private course: Course ;

  private imageURL: string;

  constructor(activatedRoute: ActivatedRoute) {
    this.imageURL = environment.URL;
    
  }


}