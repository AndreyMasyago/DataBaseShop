import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { getRejectDetails } from '../../../api/queries';


export default function RejectDetails () {
  const [data, setData] = useState([]);

  useEffect(() => { getRejectDetails().then(data => setData(data.results)); }, []);

  return (
    <div>
      <h1>Summary of rejected details</h1>

      <Table striped bordered hover variant="dark">
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