import { ITask } from 'app/shared/model/task.model';

export interface IStatus {
  id?: number;
  name?: string;
  tasks?: ITask[] | null;
}

export const defaultValue: Readonly<IStatus> = {};
