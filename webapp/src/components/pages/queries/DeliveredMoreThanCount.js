import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getDeliveredMoreThanCount } from '../../../api/queries';
import { listDetails } from '../../../api/catalog';
import { CATEGORY } from '../../../constants';
import useForm from '../../forms/formHook';

export default function DeliveredMoreThanCount () {
  const [data, setData] = useState({count: 0, results: []});

  const [details, setDetails] = useState([]);
  useEffect(() => { listDetails().then(setDetails); }, []);

  const callback = formState => getDeliveredMoreThanCount(formState).then(data => setData(data));

  const { formState, handleInputChange, handleSubmit } = useForm({
    goodsSearch: '250',
    categorySearch: 'authorized dealer',
    amountLimit: '0',
    startDate: '2019-01-01',
    endDate: '2020-12-12'
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Поставщики, поставившие определенное количество товара</h1>
        </Col>
      </Row>

      <Form onSubmit={handleSubmit}>
        <Form.Row>
          <Col lg={3}>
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
          <Col lg={3}>
            <Form.Control
              as="select"
              name="categorySearch"
              value={formState.category}
              onChange={handleInputChange}
              required
            >
              <option value="" disabled>Выберите категорию</option>
              {Object.keys(CATEGORY).map(k => (
                <option value={k}>{CATEGORY[k]}</option>
              ))}
            </Form.Control>
          </Col>
          <Col lg={3}>
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
        </Form.Row>
        <Form.Row className="search-form">
          <Col lg={3}>
            <Form.Control
              type="date"
              placeholder="С даты"
              name="startDate"
              value={formState.startDate}
              onChange={handleInputChange}
              required
            />
          </Col>
          <Col lg={3}>
            <Form.Control
              type="date"
              placeholder="По дату"
              name="endDate"
              value={formState.endDate}
              onChange={handleInputChange}
              required
            />
          </Col>
        </Form.Row>
      </Form>

      <div className="search-form">
        Количество таких поставщиков: {data.count}
      </div>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>Provider Name</th>
              </tr>
            </thead>
            <tbody>
              {data.results.map(item => (
                <tr key={item.providerId}>
                  <td><Link to={`/provider/${item.providerId}/`}>{item.providerId}</Link></td>
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
