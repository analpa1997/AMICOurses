import {Subject} from './subject.model';
import {Course} from './course.model';

export class User {
  userID?: number;
  username: string;
  password: string;
  userMail: string;
  userFirstName: string;
  userLastName: string;
  userAddress: String;
  city: string;
  country: String;
  phoneNumber: string;
  interests: string;
  urlProfileImage?: string;
  student?: boolean;
  internalName?: string;
  teaching?: Subject;
  roles?: string[];
  currentCourses?: Course[];
  completedCourses?: Course[];
}

