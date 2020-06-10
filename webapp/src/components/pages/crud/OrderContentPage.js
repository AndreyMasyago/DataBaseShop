import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import OrderContentForm from '../../forms/OrderContentForm';
import OrderContentList from '../../lists/OrderContentList';

export default function OrderContentPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <OrderContentForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <OrderContentForm />
        </Route>
        
        <Route path={match.path}>
          <h1>OrderContent</h1>
          <Link to={`${match.url}create/`}>Create orderContent</Link>
          <OrderContentList />
        </Route>
      </Switch>
    </div>
  );
}