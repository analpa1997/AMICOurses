import {Course} from './course.model';
import {Message} from './message.model';
import {User} from './user.model';
import {Studyitem} from './studyitem.model';
import {Exam} from './exam.model';

export class Subject {

  subjectID?: number;
  name: string;
  description: string;
  course: Course;
  studyItemsList: Studyitem[];
  exams: Exam[];
  messages: Message[];
  teachers: User[];
  internalName: string;
  numberModules: number;
}
