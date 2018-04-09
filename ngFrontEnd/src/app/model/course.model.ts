import {User} from './user.model';
import {Subject} from './subject.model';
import {Skill} from './skill.model';

export class Course {
  courseID?: number;
  name: string;
  internalName: string;
  courseLanguage: string;
  type: string;
  startDateString: string;
  endDateString: string;
  numberOfUsers: number;
  courseDescription: string;
  originalName: string;
  isCompleted: boolean;
  inscribedUsers: User[];
  subjects: Subject[];
  skills: Skill[];
}
