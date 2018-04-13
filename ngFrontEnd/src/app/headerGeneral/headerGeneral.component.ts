import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { LoginService } from '../login/login.service';


@Component({
  selector: 'header-general-component',
  templateUrl: './headerGeneral.component.html',
  styleUrls : ['../../assets/css/navigationHeader.css']
})



export class HeaderGeneralComponent implements OnInit {

  constructor(private loginService : LoginService, private router: Router){
  }

  ngOnInit() {
          
  }

  logOut() {
    this.loginService.logOut().subscribe(
      response => { 
        this.router.navigate(['']);
      },
      error => console.log('Error when trying to log out: ' + error)
    );
  }

}