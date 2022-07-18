import { ITask } from 'app/shared/model/task.model';

export interface ITag {
  id?: number;
  name?: string;
  color?: string;
  tasks?: ITask[] | null;
}

export const defaultValue: Readonly<ITag> = {};
