import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getBestSellers } from '../../../api/queries';


export default function BestSellers () {
  const [data, setData] = useState([]);

  useEffect(() => { getBestSellers().then(data => setData(data.results)); }, []);

  return (
    <div>
      <Row>
        <Col>
          <h1>Топ-10 наиболее продаваемых товаров</h1>
        </Col>
      </Row>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>Goods Name</th>
                <th><b>Amount</b></th>
                <th>Producer</th>
                <th>Provider Name</th>
              </tr>
            </thead>
            <tbody>
              {data.map(item => (
                <tr key={item.goodsId}>
                  <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
                  <td>{item.goodsName}</td>
                  <td><b>{item.amount}</b></td>
                  <td>{item.producer}</td>
                  <td>{item.providerName}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
