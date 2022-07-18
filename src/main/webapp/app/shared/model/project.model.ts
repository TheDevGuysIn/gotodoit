import { IOrganization } from 'app/shared/model/organization.model';
import { ITask } from 'app/shared/model/task.model';

export interface IProject {
  id?: number;
  name?: string;
  organization?: IOrganization | null;
  tasks?: ITask[] | null;
}

export const defaultValue: Readonly<IProject> = {};
