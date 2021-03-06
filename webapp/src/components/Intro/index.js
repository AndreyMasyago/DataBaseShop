import React from 'react';
import { Link } from "react-router-dom";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import './styles.css';

function Intro() {
  return (
    <Row className="Intro">
      <Col lg={6}>
        <h2>CRUD</h2>
        <div>
          <Link to="/catalog/">Catalog</Link>
        </div>
        <div>
          <Link to="/deliveryContent/">DeliveryContent</Link>
        </div>
        <div>
          <Link to="/delivery/">Delivery</Link>
        </div>
        <div>
          <Link to="/goods/">Goods</Link>
        </div>
        <div>
          <Link to="/orderContent/">OrderContent</Link>
        </div>
        <div>
          <Link to="/order/">Orders</Link>
        </div>
        <div>
          <Link to="/provider/">Providers</Link>
        </div>
        <div>
          <Link to="/reject/">Rejects</Link>
        </div>
        <div>
          <Link to="/storage/">Storage</Link>
        </div>
        <div>
          <Link to="/storageTransactions/">StorageTransactions</Link>
        </div>
      </Col>

      <Col lg={6}>
        <h2>&lsquo;Perfect&rsquo; forms</h2>

        <div>
          <Link to="/add-delivery-form/">Add delivery form (input)</Link>
        </div>

        <div>
          <Link to="/monthly-average-sales/">#6 Monthly average sales (output)</Link>
        </div>

        <h2 className="queries-header">Queries</h2>
        <div>
          <Link to="/delivered-more-than-count/">#1 Delivered more than count</Link>
        </div>
        <div>
          <Link to="/goods-details/">#2 Goods details</Link>
        </div>
        <div>
          <Link to="/orders-with-amount-filtred-by-date/">#3 Orders with amount</Link>
        </div>
        <div>
          <Link to="/stored-goods/">#4 Stored goods</Link>
        </div>
        <div>
          <Link to="/bestsellers/">#5 Bestsellers</Link>
        </div>
        <div>
          <Link to="/provider-income-stats/">#7 Provider income stats</Link>
        </div>
        <div>
          <Link to="/overhead/">#8 Overhead</Link>
        </div>
        <div>
          <Link to="/goods-rejects/">#10 Reject stats</Link>
        </div>
        <div>
          <Link to="/daily-report/">#11 Daily Report</Link>
        </div>
        <div>
          <Link to="/finance-report/">#12 Finance report</Link>
        </div>
        <div>
          <Link to="/storage-report/">#13 Storage report</Link>
        </div>
        <div>
          <Link to="/free-space/">#15 Free space</Link>
        </div>
        <div>
          <Link to="/future-orders/">#16 Future orders</Link>
        </div>
      </Col>
    </Row>
  );
}

export default Intro;
