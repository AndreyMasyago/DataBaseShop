import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import OrderForm from '../../forms/OrderForm';
import OrderEntityList from '../../lists/OrderEntityList';

export default function OrderPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <OrderForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <OrderForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Order</h1>
          <Link to={`${match.url}create/`}>Create order</Link>
          <OrderEntityList />
        </Route>
      </Switch>
    </div>
  );
}