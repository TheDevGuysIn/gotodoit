import dayjs from 'dayjs';
import { IProject } from 'app/shared/model/project.model';
import { IStatus } from 'app/shared/model/status.model';
import { IUserExtras } from 'app/shared/model/user-extras.model';
import { ITag } from 'app/shared/model/tag.model';

export interface ITask {
  id?: number;
  title?: string;
  description?: string | null;
  dueDate?: string | null;
  endDate?: string | null;
  completedDate?: string | null;
  important?: boolean | null;
  deleted?: boolean | null;
  project?: IProject | null;
  status?: IStatus | null;
  owner?: IUserExtras | null;
  parent?: ITask | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<ITask> = {
  important: false,
  deleted: false,
};
