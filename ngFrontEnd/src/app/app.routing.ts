import { Routes, RouterModule } from '@angular/router';

import { OneCourseComponent } from './course-information/one-course.component';
import { SkillsComponent } from './course-information/skills.component';
import { SubjectsComponent } from './course-information/subjects.component';
import { IndexPageComponent } from './indexPage/indexPage.component'
import { LoginComponent } from './login/login.component';
import { MoodleComponent } from './moodle/moodle.component';
import { ErrorPage } from './error/errorPage.component';
import {  SignupComponent} from './signup/signup.component';
import { AddCourseToUserComponent} from './course-information/addCourse-toUser.component';
import { SubjectListComponent } from './subjectList/subjectList.component';
import { UserComponent } from './user/user.component';
import { UserUpdateComponent } from './user/user-update.component';
import {AdminTablesComponent} from './admin/adminTables.component';
import {AdminTeacherComponent} from './admin/adminTeacher.component';
import {AdminCourseComponent} from './admin/adminCourse.component';


const appRoutes = [
  { path : 'index', component : IndexPageComponent,  useAsDefault: true },
  { path: 'login', component: LoginComponent},
  { path: 'oneCourse/:id', component: OneCourseComponent },
  { path: 'oneCourse/:id/skills', component: SkillsComponent },
  { path: 'oneCourse/:id/subjects', component: SubjectsComponent},
  { path: 'moodle/:courseName/:subjectName', component: MoodleComponent},
  { path: 'admin/course-overview/:courseName', component : SubjectListComponent},
  { path: 'error/:error', component: ErrorPage},
  { path: 'signup', component: SignupComponent},
  { path: 'oneCourse/:id/add', component: AddCourseToUserComponent},
  { path: 'users/:internalName/profile', component: UserComponent},
  { path: 'users/:internalName/profile/update', component: UserUpdateComponent},
  { path: '', redirectTo: 'index', pathMatch: 'full'},
  { path: 'admin', component: AdminTablesComponent},
  { path: 'admin/addTeacher', component: AdminTeacherComponent},
  { path: 'admin/addCourse', component: AdminCourseComponent},
];

export const routing = RouterModule.forRoot(appRoutes);
