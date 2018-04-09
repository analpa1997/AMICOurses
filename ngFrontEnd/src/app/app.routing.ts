import { Routes, RouterModule } from '@angular/router';

import { OneCourseComponent } from './course-information/one-course.component';
import { SkillsComponent } from './course-information/skills.component';
import { SubjectsComponent } from './course-information/subjects.component';

const appRoutes = [
  { path: 'oneCourse/:id', component: OneCourseComponent },
  { path: 'skills/:id/skills', component: SkillsComponent },
  { path: 'subjects/:id/subjects', component: SubjectsComponent},
  { path: '', redirectTo: 'index', pathMatch: 'full' }
];

export const routing = RouterModule.forRoot(appRoutes);
