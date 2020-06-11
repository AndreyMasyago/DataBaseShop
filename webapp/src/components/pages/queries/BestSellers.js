import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { getBestSellers } from '../../../api/queries';


export default function BestSellers () {
  const [data, setData] = useState([]);

  useEffect(() => { getBestSellers().then(data => setData(data.results)); }, []);

  return (
    <div>
      <h1>Top 5 best selling goods</h1>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Goods ID</th>
            <th>Goods Name</th>
            <th><b>Amount</b></th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.goodsId}>
              <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
              <td>{item.goodsName}</td>
              <td><b>{item.amount}</b></td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}