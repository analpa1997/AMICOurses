import {Subject} from './subject.model';
import {Practices} from './practices.model';


export class Studyitem {
  studyItemID?: number;
  type: string;
  name: string;
  fileName: string;
  subject: Subject;
  module: number;
  originalName: string;
  extension: string;
  icon: string;
  isPractice: boolean;
  practices: Practices[];
}

