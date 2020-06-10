import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listOrderEntity, deleteOrderEntity } from '../../../api/orderEntity';

export default function GoodsList() {
  const [data, setData] = useState([]);

  useEffect(() => { listOrderEntity().then(setData); }, []);

  function deleteItem(itemId) {
    deleteOrderEntity(itemId)
      .then(listOrderEntity)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover variant="dark">
      <thead>
        <tr>
          <th>#</th>
          <th>Order Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.orderId}>
            <td><Link to={`${match.url}${item.orderId}/`}>{item.orderId}</Link></td>
            <td>{item.orderDate}</td>
            <td><span className="controls" onClick={() => deleteItem(item.orderId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
