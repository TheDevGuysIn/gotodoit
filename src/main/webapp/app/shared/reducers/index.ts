import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import organization from 'app/entities/organization/organization.reducer';
// prettier-ignore
import project from 'app/entities/project/project.reducer';
// prettier-ignore
import task from 'app/entities/task/task.reducer';
// prettier-ignore
import tag from 'app/entities/tag/tag.reducer';
// prettier-ignore
import status from 'app/entities/status/status.reducer';
// prettier-ignore
import userExtras from 'app/entities/user-extras/user-extras.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  organization,
  project,
  task,
  tag,
  status,
  userExtras,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
