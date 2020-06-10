import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import DeliveryForm from '../../forms/DeliveryForm';
import DeliveryList from '../../lists/DeliveryList';

export default function DeliveryPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <DeliveryForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <DeliveryForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Delivery</h1>
          <Link to={`${match.url}create/`}>Create delivery</Link>
          <DeliveryList />
        </Route>
      </Switch>
    </div>
  );
}