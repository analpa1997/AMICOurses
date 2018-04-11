import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from './course.service';
import { User } from '../model/user.model';
import { Skill } from '../model/skill.model';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class SkillsComponent implements OnInit {

  user: User;
  skills: Skill;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    const courseID = activatedRoute.snapshot.params['id'];
    this.courseService.skills(courseID).subscribe(data => {
      this.skills = data;
      this.user = data['user'];
    });
  }
  ngOnInit() {
  }
}
