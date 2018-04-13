import { Routes, RouterModule } from '@angular/router';

import { OneCourseComponent } from './course-information/one-course.component';
import { SkillsComponent } from './course-information/skills.component';
import { SubjectsComponent } from './course-information/subjects.component';
import { IndexPageComponent } from './indexPage/indexPage.component'
import { LoginComponent } from './login/login.component';

const appRoutes = [
  { path : 'index', component : IndexPageComponent,  useAsDefault: true },
  { path: 'oneCourse/:id', component: OneCourseComponent },
  { path: 'skills/:id/skills', component: SkillsComponent },
  { path: 'subjects/:id/subjects', component: SubjectsComponent},
  { path: 'login', component: LoginComponent},
  { path: '', redirectTo: 'index', pathMatch: 'full'},
];

export const routing = RouterModule.forRoot(appRoutes);
