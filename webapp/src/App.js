import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

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
import DailyReport from './components/pages/queries/DailyReport';
import StorageReport from './components/pages/queries/StorageReport';
import DeliveredMoreThanCount from './components/pages/queries/DeliveredMoreThanCount';
import OrdersWithAmountFiltredByDate from './components/pages/queries/OrdersWithAmountFiltredByDate';
import StoredGoods from './components/pages/queries/StoredGoods';
import FreeSpace from './components/pages/queries/FreeSpace';
import FutureOrders from './components/pages/queries/FutureOrders';

import AddDeliveryForm from './components/forms/AddDeliveryForm';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  const [savedState, setSavedState] = useState({});

  return (
    <Container className="App">
      <Router>
        <Navbar>
          <Navbar.Brand><Link to="/">AutoShop</Link></Navbar.Brand>
        </Navbar>
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

          {/* Perfect forms */}

          <Route path="/add-delivery-form/">
            <AddDeliveryForm saveFormState={setSavedState} lastFormState={savedState} />
          </Route>

          {/* Queries */}

          <Route path="/bestsellers/">
            <BestSellers />
          </Route>

          <Route path="/goods-details/">
            <GoodsDetails />
          </Route>

          <Route path="/goods-rejects/">
            <Row>
              <Col>
                <h1>Возвраты</h1>
              </Col>
            </Row>

            <Tabs defaultActiveKey="details" id="rejects-tabs">
              <Tab eventKey="details" title="Детали">
                <RejectDetails />
              </Tab>

              <Tab eventKey="providers" title="Поставщики">
                <RejectProviders />
              </Tab>
            </Tabs>
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

          <Route path="/daily-report/">
            <DailyReport />
          </Route>

          <Route path="/storage-report/">
            <StorageReport />
          </Route>

          <Route path="/delivered-more-than-count/">
            <DeliveredMoreThanCount />
          </Route>

          <Route path="/orders-with-amount-filtred-by-date/">
            <OrdersWithAmountFiltredByDate />
          </Route>

          <Route path="/stored-goods/">
            <StoredGoods />
          </Route>

          <Route path="/free-space/">
            <FreeSpace />
          </Route>

          <Route path="/future-orders/">
            <FutureOrders />
          </Route>

        </Switch>
      </Router>
    </Container>
  );
}

export default App;
