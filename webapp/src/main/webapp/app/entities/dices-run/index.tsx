import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DicesRun from './dices-run';
import DicesRunDetail from './dices-run-detail';
import DicesRunUpdate from './dices-run-update';
import DicesRunDeleteDialog from './dices-run-delete-dialog';

const DicesRunRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DicesRun />} />
    <Route path="new" element={<DicesRunUpdate />} />
    <Route path=":id">
      <Route index element={<DicesRunDetail />} />
      <Route path="edit" element={<DicesRunUpdate />} />
      <Route path="delete" element={<DicesRunDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DicesRunRoutes;
