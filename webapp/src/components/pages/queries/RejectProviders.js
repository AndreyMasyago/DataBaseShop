import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getProvidersWithReject } from '../../../api/queries';


export default function RejectProviders () {
  const [data, setData] = useState([]);

  useEffect(() => { getProvidersWithReject().then(data => setData(data.results)); }, []);

  return (
    <Row className="report-body">
      <Col>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>#</th>
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
      </Col>
    </Row>
  );
}
