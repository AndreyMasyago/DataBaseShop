import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listStorageTransactions, deleteStorageTransaction } from '../../../api/storageTransactions';

export default function StorageTransactionsList() {
  const [data, setData] = useState([]);

  useEffect(() => { listStorageTransactions().then(setData); }, []);

  function deleteItem(itemId) {
    deleteStorageTransaction(itemId)
      .then(listStorageTransactions)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover variant="dark">
      <thead>
        <tr>
          <th>#</th>
          <th>Transaction Date</th>
          <th>Cells Id</th>
          <th>Amount</th>
          <th>Goods Name</th>
          <th>Producer</th>
          <th>Provider</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.storageTransactionId}>
            <td><Link to={`${match.url}${item.storageTransactionId}/`}>{item.storageTransactionId}</Link></td>
            <td>{item.transactionDate}</td>
            <td>{item.storage.cellsId}</td>
            <td>{item.amount}</td>
            <td>{item.goods.catalog.goodsName}</td>
            <td>{item.goods.producer}</td>
            <td>{item.goods.provider.providerName}</td>
            <td><span className="controls" onClick={() => deleteItem(item.storageTransactionId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
