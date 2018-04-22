import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { ChartsModule } from 'ng2-charts';

import { AppComponent } from './app.component';
import { HeaderComponent } from './course-information/header.component';
import { FooterComponent } from './course-information/footer.component';
import { CourseService } from './course-information/course.service';
import { OneCourseComponent } from './course-information/one-course.component';
import { SkillsComponent } from './course-information/skills.component';
import { SubjectsComponent } from './course-information/subjects.component';
import { routing } from './app.routing';
import { IndexPageComponent } from './indexPage/indexPage.component';
import { HeaderGeneralComponent } from './headerGeneral/headerGeneral.component';
import { CourseIndexComponent } from './indexPage/courseIndex.component';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';
import { HttpModule } from '@angular/http';
import { MoodleComponent } from './moodle/moodle.component';
import { MoodleService } from './moodle/moodle.service';
import { MoodleContentsComponent } from './moodle/moodleContents.component';
import { AddCourseToUserComponent } from './course-information/addCourse-toUser.component';
import { SignupComponent } from './signup/signup.component';
import {UserService} from './user/user.service';
import { MoodleEvaluationComponent } from './moodle/moodleEvaluation.component';
import { MoodleProgressComponent } from './moodle/moodleProgress.component';

import {NgbdCarouselBasic} from './indexPage/carousel-basic';
import { SubjectListComponent } from './subjectList/subjectList.component';
import { SubjectListService } from './subjectList/subjectList.service';
import { FormsModule } from '@angular/forms';
import {UserComponent} from './user/user.component';
import {UserUpdateComponent} from './user/user-update.component';
import { ErrorPage } from './error/errorPage.component';



@NgModule({declarations: [AppComponent, HeaderComponent, FooterComponent, OneCourseComponent,
                          SkillsComponent, SubjectsComponent, IndexPageComponent, HeaderGeneralComponent,
                          CourseIndexComponent, LoginComponent, MoodleComponent, ErrorPage, MoodleContentsComponent,
                          AddCourseToUserComponent, SignupComponent, MoodleEvaluationComponent, MoodleProgressComponent, NgbdCarouselBasic,
    SubjectListComponent, UserComponent, UserUpdateComponent],
  imports: [BrowserModule, routing, HttpClientModule,  HttpModule  , NgbModule.forRoot(), AngularFontAwesomeModule, ChartsModule,
    FormsModule],
  providers: [CourseService, LoginService, MoodleService, UserService, SubjectListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
