import React from 'react';
import { Link } from "react-router-dom";

import './styles.css';

function Intro() {
  return (
    <div className="Intro">
      <div className="Intro-left">
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
      </div>

      <div className="Intro-right">
        <div>
          <Link to="/goods-details/">#2. Goods details</Link>
        </div>
        <div>
          <Link to="/bestsellers/">#5. BestSellers</Link>
        </div>
        <div>
          <Link to="/order-content/monthly-average-sales/">#6. Monthly Average Sales</Link>
        </div>
        <div>
          <Link to="/order-content/provider-income-stats/">#7. Provider Income Stats</Link>
        </div>
        <div>
          <Link to="/order-content/overhead/">#8. OverHead</Link>
        </div>
        <div>
          <Link to="/goods-rejects/">#10.1 Reject details</Link>
        </div>
        <div>
          <Link to="/reject-providers/">#10.2 Reject providers</Link>
        </div>
      </div>
    </div>
  );
}

export default Intro;
