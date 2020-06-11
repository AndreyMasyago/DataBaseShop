import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listProviders, deleteProvider } from '../../../api/provider';
import { CATEGORY } from '../../../constants';

export default function ProviderList() {
  const [data, setData] = useState([]);

  useEffect(() => { listProviders().then(setData); }, []);

  function deleteItem(itemId) {
    deleteProvider(itemId)
      .then(listProviders)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Provider Name</th>
          <th>Category</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.providerId}>
            <td><Link to={`${match.url}${item.providerId}/`}>{item.providerId}</Link></td>
            <td>{item.providerName}</td>
            <td>{CATEGORY[item.category]}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.providerId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
