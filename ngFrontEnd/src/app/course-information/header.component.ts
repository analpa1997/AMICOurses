import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';


@Component({
  selector: 'app-course-information-header',
  templateUrl: './header.component.html',
  styleUrls: ['./clean-blog.component.css']
})

export class HeaderComponent implements OnInit {

  @Input()
  id?: Number;


  constructor(private router: Router) {

  }

  ngOnInit () {

  }

}
