import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from './course.service';
import { Skill } from '../model/skill.model';


@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class SkillsComponent implements OnInit {


  skills: Skill[];
  courseID: number;
  constructor(private router: Router, private courseService: CourseService, private activatedRoute: ActivatedRoute) {

  }

  ngOnInit() {
    this.courseID = this.activatedRoute.snapshot.params['id'];
    this.courseService.skills(this.courseID).subscribe(skills => this.skills = skills,
      error => console.log(error));
  }
}


