import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listDeliveries, deleteDelivery } from '../../../api/delivery';

export default function DeliveriesList() {
  const [data, setData] = useState([]);

  useEffect(() => { listDeliveries().then(setData); }, []);

  function deleteItem(itemId) {
    deleteDelivery(itemId)
      .then(listDeliveries)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover variant="dark">
      <thead>
        <tr>
          <th>#</th>
          <th>Arriving date on storage</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.deliveryId}>
            <td><Link to={`${match.url}${item.deliveryId}/`}>{item.deliveryId}</Link></td>
            <td>{item.arrivingDateOnStorage}</td>
            <td><span className="controls" onClick={() => deleteItem(item.deliveryId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
