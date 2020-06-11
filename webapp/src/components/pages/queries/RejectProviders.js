import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { getProvidersWithReject } from '../../../api/queries';


export default function RejectProviders () {
  const [data, setData] = useState([]);

  useEffect(() => { getProvidersWithReject().then(data => setData(data.results)); }, []);

  return (
    <div>
      <h1>Providers with rejects</h1>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Provider ID</th>
            <th>Provider Name</th>
            <th>Category</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.providerId}>
              <td><Link to={`/provider/${item.providerId}/`}>{item.providerId}</Link></td>
              <td>{item.providerName}</td>
              <td>{item.category}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}