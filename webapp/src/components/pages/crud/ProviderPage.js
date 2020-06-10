import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import ProvidersForm from '../../forms/ProvidersForm';
import ProviderList from '../../lists/ProviderList';

export default function ProviderPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <ProvidersForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <ProvidersForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Providers</h1>
          <Link to={`${match.url}create/`}>Create provider</Link>
          <ProviderList />
        </Route>
      </Switch>
    </div>
  );
}