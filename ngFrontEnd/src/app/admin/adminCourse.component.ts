import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../course-information/course.service';
import { User } from '../model/user.model';
import { Subject } from '../model/subject.model';
import {Course} from '../model/course.model';
import {UserService} from '../user/user.service';
import {environment} from '../../environments/environment';
import {Skill} from '../model/skill.model';

@Component({
  selector: 'admin-course-component',
  templateUrl: './adminCourse.component.html',
  styleUrls : ['../../assets/css/sb-admin.css']
})

export class AdminCourseComponent {
  constructor(private router: Router, private courseService: CourseService, private userService: UserService){}

  private skills: Skill[] = [];

  submitForm(name: string, dateStart: string, dateEnd: string, language: string, type: string,
          skillOne: String, skillTwo: String, skillThree: String, description: string){
      let course: Course;
      let skill1: Skill;
      let skill2: Skill;
      let skill3: Skill;
      skill1 = <Skill>{skillName: skillOne, skillDescription:""};
      this.skills.push(skill1);
      if(skillTwo.length > 0){
        skill2 = <Skill>{skillName: skillTwo, skillDescription:""};
        this.skills.push(skill2);
      }
      if(skillThree.length > 0) {
        skill3 = <Skill>{skillName: skillThree, skillDescription:""};
        this.skills.push(skill3);
      }
      const internalName = name.replace(' ', '-').toLowerCase();
      course = <Course>{internalName: internalName, name: name, startDateString: dateStart, endDateString: dateEnd,
                        courseLanguage: language, type: type, courseDescription: description, skills: this.skills};
      this.courseService.createCourse(course).subscribe(
        user => this.router.navigate(['/admin']),
        error => console.error('Error creating new course: ' + error)
      );
  }

}
