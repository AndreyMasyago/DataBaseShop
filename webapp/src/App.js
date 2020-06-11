import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';

import Intro from './components/Intro'
import CatalogPage from './components/pages/crud/CatalogPage';
import DeliveryContentPage from './components/pages/crud/DeliveryContentPage';
import DeliveryPage from './components/pages/crud/DeliveryPage';
import GoodsPage from './components/pages/crud/GoodsPage';
import OrderContentPage from './components/pages/crud/OrderContentPage';
import OrderPage from './components/pages/crud/OrderPage';
import ProviderPage from './components/pages/crud/ProviderPage';
import RejectPage from './components/pages/crud/RejectPage';
import StoragePage from './components/pages/crud/StoragePage';
import StorageTransactionsPage from './components/pages/crud/StorageTransactionsPage';

import BestSellers from './components/pages/queries/BestSellers';
import GoodsDetails from './components/pages/queries/GoodsDetails';
import RejectDetails from './components/pages/queries/RejectDetails';
import RejectProviders from './components/pages/queries/RejectProviders';
import MonthlyAverageSales from './components/pages/queries/MonthlyAverageSales';
import ProviderIncomeStats from './components/pages/queries/ProviderIncomeStats';
import OverHead from './components/pages/queries/OverHead';
import FinanceReport from './components/pages/queries/FinanceReport';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <Container className="App">
      <Navbar>
        <Navbar.Brand href="/">Home</Navbar.Brand>
      </Navbar>

      <div>
        <Router>
            {/* A <Switch> looks through its children <Route>s and
                renders the first one that matches the current URL. */}
            <Switch>
              <Route exact path="/">
                <Intro />
              </Route>

              <Route path="/catalog/">
                <CatalogPage />
              </Route>

              <Route path="/deliveryContent/">
                <DeliveryContentPage />
              </Route>

              <Route path="/delivery/">
                <DeliveryPage />
              </Route>

              <Route path="/goods/">
                <GoodsPage />
              </Route>

              <Route path="/orderContent/">
                <OrderContentPage />
              </Route>

              <Route path="/order/">
                <OrderPage />
              </Route>

              <Route path="/provider/">
                <ProviderPage />
              </Route>

              <Route path="/reject/">
                <RejectPage />
              </Route>

              <Route path="/storage/">
                <StoragePage />
              </Route>

              <Route path="/StorageTransactions/">
                <StorageTransactionsPage />
              </Route>

              <Route path="/bestsellers/">
                <BestSellers />
              </Route>

              <Route path="/goods-details/">
                <GoodsDetails />
              </Route>

              <Route path="/goods-rejects/">
                <RejectDetails />
              </Route>

              <Route path="/reject-providers/">
                <RejectProviders />
              </Route>

              <Route path="/monthly-average-sales/">
                <MonthlyAverageSales />
              </Route>

              <Route path="/provider-income-stats/">
                <ProviderIncomeStats />
              </Route>

              <Route path="/overhead/">
                <OverHead />
              </Route>

              <Route path="/finance-report/">
                <FinanceReport />
              </Route>

            </Switch>
        </Router>
      </div>
    </Container>
  );
}

export default App;
