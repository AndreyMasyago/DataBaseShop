import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listDeliveryContent, deleteDeliveryContent } from '../../../api/deliveryContent';

export default function DeliveryContentList() {
  const [data, setData] = useState([]);

  useEffect(() => { listDeliveryContent().then(setData); }, []);

  function deleteItem(itemId) {
    deleteDeliveryContent(itemId)
      .then(listDeliveryContent)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Arriving Date On Storage</th>
          <th>Amount</th>
          <th>Goods Name</th>
          <th>Producer</th>
          <th>Provider</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.deliveryContentId}>
            <td><Link to={`${match.url}${item.deliveryContentId}/`}>{item.deliveryContentId}</Link></td>
            <td>{item.delivery.arrivingDateOnStorage}</td>
            <td>{item.amount}</td>
            <td>{item.goods.catalog.goodsName}</td>
            <td>{item.goods.producer}</td>
            <td>{item.goods.provider.providerName}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.deliveryContentId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
