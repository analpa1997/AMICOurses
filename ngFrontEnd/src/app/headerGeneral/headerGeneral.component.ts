import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';


@Component({
  selector: 'header-general-component',
  templateUrl: './headerGeneral.component.html',
  styleUrls : ['../../assets/css/navigationHeader.css']
})



export class HeaderGeneralComponent implements OnInit {

  constructor(){
  }
  ngOnInit() {
          
  }

}