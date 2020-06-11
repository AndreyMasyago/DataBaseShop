import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getGoodsDetails } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function GoodsDetails () {
  const [data, setData] = useState([]);

  const callback = formState => getGoodsDetails(formState).then(data => setData(data.results));

  const { handleInputChange, formState } = useForm({ goodsSearch: '' }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Поиск деталей</h1>
        </Col>
      </Row>

      <Form className="search-form">
        <Form.Row>
          <Col lg={3}>
            <Form.Control
              type="text"
              placeholder="Поиск по названию детали"
              name="goodsSearch"
              value={formState.goodsSearch}
              onChange={handleInputChange}
            />
          </Col>
        </Form.Row>
      </Form>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Goods ID</th>
                <th>Goods Name</th>
                <th>Provider Name</th>
                <th>Purchase Price</th>
                <th>Delivery Time</th>
              </tr>
            </thead>
            <tbody>
              {data.map(item => (
                <tr key={item.goodsId}>
                  <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
                  <td>{item.goodsName}</td>
                  <td>{item.providerName}</td>
                  <td>{item.purchasePrice}</td>
                  <td>{item.deliveryTime}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
