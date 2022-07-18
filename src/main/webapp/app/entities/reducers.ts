import organization from 'app/entities/organization/organization.reducer';
import project from 'app/entities/project/project.reducer';
import task from 'app/entities/task/task.reducer';
import tag from 'app/entities/tag/tag.reducer';
import status from 'app/entities/status/status.reducer';
import userExtras from 'app/entities/user-extras/user-extras.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  organization,
  project,
  task,
  tag,
  status,
  userExtras,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
