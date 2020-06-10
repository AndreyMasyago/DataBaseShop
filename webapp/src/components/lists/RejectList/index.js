import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listRejects, deleteReject } from '../../../api/reject';

export default function RejectList() {
  const [data, setData] = useState([]);

  useEffect(() => { listRejects().then(setData); }, []);

  function deleteItem(itemId) {
    deleteReject(itemId)
      .then(listRejects)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover variant="dark">
      <thead>
        <tr>
          <th>#</th>
          <th>Order Date</th>
          <th>Amount</th>
          <th>Goods Name</th>
          <th>Producer</th>
          <th>Provider</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.rejectId}>
            <td><Link to={`${match.url}${item.rejectId}/`}>{item.rejectId}</Link></td>
            <td>{item.orderEntity.orderDate}</td>
            <td>{item.amount}</td>
            <td>{item.goods.catalog.goodsName}</td>
            <td>{item.goods.producer}</td>
            <td>{item.goods.provider.providerName}</td>
            <td><span className="controls" onClick={() => deleteItem(item.rejectId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
