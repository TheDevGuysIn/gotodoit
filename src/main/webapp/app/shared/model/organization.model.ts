import { IProject } from 'app/shared/model/project.model';
import { IUserExtras } from 'app/shared/model/user-extras.model';

export interface IOrganization {
  id?: number;
  name?: string;
  imageContentType?: string | null;
  image?: string | null;
  projects?: IProject[] | null;
  users?: IUserExtras[] | null;
}

export const defaultValue: Readonly<IOrganization> = {};
