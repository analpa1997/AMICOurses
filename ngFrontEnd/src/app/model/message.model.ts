import {User} from './user.model';
import {Subject} from './subject.model';

export class Message {
  messageID?: number;
  topic: string;
  text: string;
  folder: string;
  date: Date;
  author: User;
  receivers: User[];
  subject: Subject;
}
