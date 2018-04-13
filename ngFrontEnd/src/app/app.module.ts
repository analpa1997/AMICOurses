import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

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

@NgModule({declarations: [AppComponent, HeaderComponent, FooterComponent, OneCourseComponent,
    SkillsComponent, SubjectsComponent, IndexPageComponent, HeaderGeneralComponent, CourseIndexComponent, LoginComponent],
  imports: [BrowserModule, routing, HttpClientModule,  HttpModule  ,NgbModule.forRoot()],
  providers: [CourseService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }