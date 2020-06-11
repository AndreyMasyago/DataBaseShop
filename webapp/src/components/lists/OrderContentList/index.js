import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listOrderContent, deleteOrderContent } from '../../../api/orderContent';

export default function GoodsList() {
  const [data, setData] = useState([]);

  useEffect(() => { listOrderContent().then(setData); }, []);

  function deleteItem(itemId) {
    deleteOrderContent(itemId)
      .then(listOrderContent)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
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
          <tr key={item.orderContentId}>
            <td><Link to={`${match.url}${item.orderContentId}/`}>{item.orderContentId}</Link></td>
            <td>{item.orderEntity.orderDate}</td>
            <td>{item.amount}</td>
            <td>{item.goods.catalog.goodsName}</td>
            <td>{item.goods.producer}</td>
            <td>{item.goods.provider.providerName}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.orderContentId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
