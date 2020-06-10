import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import DeliveryContentForm from '../../forms/DeliveryContentForm';
import DeliveryContentList from '../../lists/DeliveryContentList';

export default function DeliveryContentPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>               
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <DeliveryContentForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <DeliveryContentForm />
        </Route>
        
        <Route path={match.path}>
          <h1>DeliveryContent</h1>
          <Link to={`${match.url}create/`}>Create deliveryContent</Link>
          <DeliveryContentList />
        </Route>
      </Switch>
    </div>
  );
}