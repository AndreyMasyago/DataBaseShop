import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getOrdersWithAmountFiltredByDate } from '../../../api/queries';
import { listDetails } from '../../../api/catalog';
import useForm from '../../forms/formHook';

export default function OrdersWithAmountFiltredByDate () {
  const [data, setData] = useState({count: 0, results: []});

  const [details, setDetails] = useState([]);
  useEffect(() => { listDetails().then(setDetails); }, []);

  const callback = formState => getOrdersWithAmountFiltredByDate(formState).then(data => setData(data));

  const { formState, handleInputChange, handleSubmit } = useForm({
    goodsSearch: '250',
    amountLimit: '1',
    orderDateFrom: '2019-01-01',
    orderDateTo: '2020-12-12'
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  console.log(data);

  return (
    <div>
      <Row>
        <Col>
          <h1>Заказы определенного товара</h1>
        </Col>
      </Row>

      <Form onSubmit={handleSubmit}>
        <Form.Row>
          <Col lg={4}>
            <Form.Control
              as="select"
              name="goodsSearch"
              value={formState.goodsSearch}
              onChange={handleInputChange}
              required
            >
              <option value="" disabled>Выберите деталь</option>
              {details.map(d => (
                <option key={d.detailId} value={d.detailId}>{d.goodsName}</option>
              ))}
            </Form.Control>
          </Col>
          <Col lg={2}>
            <Form.Control
              type="number"
              min="0"
              placeholder="Минимальное пороговое значение"
              name="amountLimit"
              value={formState.amountLimit}
              onChange={handleInputChange}
              required
            />
          </Col>
          <Col lg={3}>
            <Form.Control
              type="date"
              placeholder="С даты"
              name="orderDateFrom"
              value={formState.orderDateFrom}
              onChange={handleInputChange}
              required
            />
          </Col>
          <Col lg={3}>
            <Form.Control
              type="date"
              placeholder="По дату"
              name="orderDateTo"
              value={formState.orderDateTo}
              onChange={handleInputChange}
              required
            />
          </Col>
        </Form.Row>
      </Form>

      <div className="search-form">
        <b>Количество таких чеков: {data.count}</b>
      </div>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>Order Date</th>
                <th>Amount</th>
                <th>Producer</th>
                <th>Provider Name</th>
              </tr>
            </thead>
            <tbody>
              {data.results.map(item => (
                <tr key={item.orderContentId}>
                  <td><Link to={`/orderContent/${item.orderContentId}/`}>{item.orderContentId}</Link></td>
                  <td>{item.orderEntity.orderDate}</td>
                  <td>{item.amount}</td>
                  <td>{item.goods.producer}</td>
                  <td>{item.goods.provider.providerName}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
