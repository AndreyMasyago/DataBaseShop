import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listDetails , deleteDetail } from '../../../api/catalog';

export default function CatalogList() {
  const [data, setData] = useState([]);

  useEffect(() => { listDetails().then(setData); }, []);

  function deleteItem(itemId) {
    deleteDetail(itemId)
      .then(listDetails)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Goods Name</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.detailId}>
            <td><Link to={`${match.url}${item.detailId}/`}>{item.detailId}</Link></td>
            <td>{item.goodsName}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.detailId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
