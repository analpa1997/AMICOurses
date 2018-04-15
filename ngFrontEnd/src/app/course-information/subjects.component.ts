import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from './course.service';
import { User } from '../model/user.model';
import { Subject } from '../model/subject.model';


@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./clean-blog.component.css']
})

export class SubjectsComponent implements OnInit {
  user: User;
  subjects: Subject[];
  courseID: number;

  constructor(private router: Router, private courseService: CourseService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.courseID = this.activatedRoute.snapshot.params['id'];
    this.courseService.subjects(this.courseID).subscribe(subjects => this.subjects = subjects,
        error => console.error(error)
    );
  }
}

