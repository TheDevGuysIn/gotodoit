import { IUser } from 'app/shared/model/user.model';
import { IOrganization } from 'app/shared/model/organization.model';

export interface IUserExtras {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
  user?: IUser | null;
  organizations?: IOrganization[] | null;
}

export const defaultValue: Readonly<IUserExtras> = {};
