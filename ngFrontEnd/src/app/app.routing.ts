import { Routes, RouterModule } from '@angular/router';

import { OneCourseComponent } from './course-information/one-course.component';
import { SkillsComponent } from './course-information/skills.component';
import { SubjectsComponent } from './course-information/subjects.component';
import { IndexPageComponent } from './indexPage/indexPage.component'
import { LoginComponent } from './login/login.component';
import { MoodleComponent } from './moodle/moodle.component';
import { Error404 } from './error/error404.component';

const appRoutes = [
  { path : 'index', component : IndexPageComponent,  useAsDefault: true },
  { path: 'login', component: LoginComponent},
  { path: 'oneCourse/:id', component: OneCourseComponent },
  { path: 'oneCourse/:id/skills', component: SkillsComponent },
  { path: 'oneCourse/:id/subjects', component: SubjectsComponent},
  { path: 'moodle/:courseName/:subjectName', component: MoodleComponent},
  { path: 'error404', component: Error404},

  { path: '', redirectTo: 'index', pathMatch: 'full'},
];

export const routing = RouterModule.forRoot(appRoutes);
