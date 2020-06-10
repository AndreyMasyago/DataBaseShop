import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import StorageForm from '../../forms/StorageForm';
import StorageList from '../../lists/StorageList';

export default function StoragePage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <StorageForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <StorageForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Storage</h1>
          <Link to={`${match.url}create/`}>Create storage</Link>
          <StorageList />
        </Route>
      </Switch>
    </div>
  );
}