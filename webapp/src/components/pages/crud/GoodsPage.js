import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import GoodsForm from '../../forms/GoodsForm';
import GoodsList from '../../lists/GoodsList';

export default function GoodsPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <GoodsForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <GoodsForm />
        </Route>
        
        <Route path={match.path}>
          <h1>Товары</h1>
          <Link to={`${match.url}create/`}>Добавить товар</Link>
          <GoodsList />
        </Route>
      </Switch>
    </div>
  );
}