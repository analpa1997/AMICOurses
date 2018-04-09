import {User} from './user.model';
import {Studyitem} from './studyitem.model';

export class Practices {
  practiceID?: number;
  practiceName: string;
  calification: number;
  studyItem: Studyitem;
  owner: User;
  originalName: string;
  presented: boolean;
  corrected: boolean;
}
