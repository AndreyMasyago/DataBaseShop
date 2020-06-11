import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Pagination from 'react-bootstrap/Pagination';

import { getMonthlyAverageSales } from '../../../api/queries';
import { listDetails } from '../../../api/catalog';
import useForm from '../../forms/formHook';

const EMPTY_FORM = {
  detailId: '',
  month: '',
  amountFrom: '',
  amountTo: '',
  producerSearch: '',
  orderBy: '',
  page: 0,
  pageSize: 4
};


export default function MonthlyAverageSales () {

  const [data, setData] = useState({count: 0, results: []});

  const callback = formState => {
    const filteredState = Object.keys(formState).reduce((filtered, key) => {
      if (formState[key]) {
        filtered[key] = formState[key];
      }
      return filtered;
    }, {});

    return getMonthlyAverageSales(filteredState).then(data => setData(data));
  };

  const { handleInputChange, formState, setFormState } = useForm(EMPTY_FORM, callback);

  useEffect(() => { callback(formState) }, [formState]);

  const [details, setDetails] = useState([]);
  useEffect(() => { listDetails().then(setDetails); }, []);

  let numPages = Math.floor(data.count / formState.pageSize);
  if (data.count % formState.pageSize !== 0) {
    numPages += 1;
  }

  return (
    <div>
      <Row>
        <Col>
          <h1>Средние продажи по месяцам</h1>
        </Col>
      </Row>

      <Form>
        <Form.Row className="search-form">
          <Col lg={4}>
            <Form.Control
              as="select"
              name="detailId"
              value={formState.detailId}
              onChange={handleInputChange}
              required
            >
              <option value="">Деталь</option>
              {details.map(d => (
                <option key={d.detailId} value={d.detailId}>{d.goodsName}</option>
              ))}
            </Form.Control>
          </Col>

          <Col lg={2}>
            <Form.Control
              as="select"
              name="month"
              value={formState.month}
              onChange={handleInputChange}
              required
            >
              <option value="">Месяц</option>
              {[...Array(12).keys()].map(m => (
                <option key={m + 1} value={m + 1}>{m + 1}</option>
              ))}
            </Form.Control>
          </Col>

          <Col lg={4}>
            <Form.Control
              type="text"
              placeholder="Производитель"
              name="producerSearch"
              value={formState.producerSearch}
              onChange={handleInputChange}
            />
          </Col>
        </Form.Row>

        <Form.Row className="search-form">
          <Col lg={3}>
            <Form.Control
              type="number"
              placeholder="Количество от"
              name="amountFrom"
              value={formState.amountFrom}
              onChange={handleInputChange}
            />
          </Col>

          <Col lg={3}>
            <Form.Control
              type="number"
              placeholder="Количество до"
              name="amountTo"
              value={formState.amountTo}
              onChange={handleInputChange}
            />
          </Col>

          <Col lg={4}>
            <Form.Control
              as="select"
              name="orderBy"
              value={formState.orderBy}
              onChange={handleInputChange}
              required
            >
              <option value="">Сортировка (по умолчанию)</option>
              <option value="goodsName">Название детали &#x2B06;</option>
              <option value="-goodsName">Название детали &#x2B07;</option>
              <option value="month">Месяц &#x2B06;</option>
              <option value="-month">Месяц &#x2B07;</option>
              <option value="producer">Производитель &#x2B06;</option>
              <option value="-producer">Производитель &#x2B07;</option>
              <option value="amount">Количество &#x2B06;</option>
              <option value="-amount">Количество &#x2B07;</option>

            </Form.Control>
          </Col>
        </Form.Row>
      </Form>

      <Row className="report-body">
        <Col>

          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Goods ID</th>
                <th>Month</th>
                <th>Producer</th>
                <th><b>Amount</b></th>
              </tr>
            </thead>
            <tbody>
              {data.results.map(item => (
                <tr key={`${item.goodsId}_${item.month}`}>
                  <td><Link to={`/catalog/${item.detailId}/`}>{item.goodsName}</Link></td>
                  <td>{item.month}</td>
                  <td>{item.producer}</td>
                  <td><b>{item.amount}</b></td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>

      <Row>
        <Col>
          <Pagination>
            {(numPages > 1) && [...Array(numPages).keys()].map(i => (
              <Pagination.Item
                key={i}
                active={i === formState.page}
                onClick={() => setFormState({...formState, page: i})}
              >
                {i + 1}
              </Pagination.Item>
            ))}
          </Pagination>
        </Col>
      </Row>
    </div>
  );
}
