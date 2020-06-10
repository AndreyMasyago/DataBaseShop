import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import RejectForm from '../../forms/RejectForm';
import RejectList from '../../lists/RejectList';

export default function RejectPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <RejectForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <RejectForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Reject</h1>
          <Link to={`${match.url}create/`}>Create reject</Link>
          <RejectList />
        </Route>
      </Switch>
    </div>
  );
}