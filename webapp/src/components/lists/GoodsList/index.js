import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listGoods, deleteGoods } from '../../../api/goods';

export default function GoodsList() {
  const [data, setData] = useState([]);

  useEffect(() => { listGoods().then(setData); }, []);

  function deleteItem(itemId) {
    deleteGoods(itemId)
      .then(listGoods)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Catalog</th>
          <th>Provider</th>
          <th>Size</th>
          <th>Delivery Time</th>
          <th>Purchase Price</th>
          <th>Selling Price</th>
          <th>Producer</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.goodsId}>
            <td><Link to={`${match.url}${item.goodsId}/`}>{item.goodsId}</Link></td>
            <td>{item.catalog.goodsName}</td>
            <td>{item.provider.providerName}</td>
            <td>{item.size}</td>
            <td>{item.deliveryTime}</td>
            <td>{item.purchasePrice}</td>
            <td>{item.sellingPrice}</td>
            <td>{item.producer}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.goodsId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
