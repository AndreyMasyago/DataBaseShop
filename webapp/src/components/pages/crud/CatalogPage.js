import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import CatalogForm from '../../forms/CatalogForm';
import CatalogList from '../../lists/CatalogList';

export default function CatalogPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <CatalogForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <CatalogForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Catalog</h1>
          <Link to={`${match.url}create/`}>Create catalog</Link>
          <CatalogList />
        </Route>
      </Switch>
    </div>
  );
}