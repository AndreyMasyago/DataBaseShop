import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";

import StorageTransactionForm from '../../forms/StorageTransactionForm';
import StorageTransactionsList from '../../lists/StorageTransactionsList';

export default function StorageTransactionsPage() {
  const match = useRouteMatch();
  
  return (
    <div>

      <Switch>        
        <Route path={`${match.path}create/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <StorageTransactionForm />
        </Route>
        
        <Route path={`${match.path}:id/`}>
          <Link to={`${match.url}/`}>Back to list</Link>
          <StorageTransactionForm />
        </Route>
        
        <Route path={match.path}>
          <h1>StorageTransactions</h1>
          <Link to={`${match.url}create/`}>Create StorageTransaction</Link>
          <StorageTransactionsList />
        </Route>
      </Switch>
    </div>
  );
}