import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {CourseService} from './course.service';
import {User} from '../model/user.model';
import {Subject} from '../model/subject.model';


@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class SubjectsComponent implements OnInit {
  user: User;
  subjects: Subject[];

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    const courseID = activatedRoute.snapshot.params['id'];
    this.courseService.subjects(courseID).subscribe(data => {
      this.subjects = data;
      // this.user = data['user'];
    },
      error => console.error(error)
    );
  }
  ngOnInit() {
  }

}
