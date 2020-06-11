import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getProviderIncomeStats } from '../../../api/queries';
import { listProviders } from '../../../api/provider';
import useForm from '../../forms/formHook';


export default function ProviderIncomeStats () {
  const [data, setData] = useState({});

  const [providers, setProviders] = useState([]);
  useEffect(() => { listProviders().then(setProviders); }, []);

  const callback = formState => getProviderIncomeStats(formState).then(data => setData(data));

  const { formState, handleInputChange, handleSubmit } = useForm({
    providerSearch: '',
    orderDateFrom: '2019-01-01',
    orderDateTo: '2020-12-12'
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Доля продаж поставщика</h1>
        </Col>
      </Row>

      <Form onSubmit={handleSubmit} className="search-form">
          <Form.Row>
            <Col lg={3}>
              <Form.Control
                as="select"
                name="providerSearch"
                value={formState.providerSearch}
                onChange={handleInputChange}
                required
              >
                <option value="" disabled>Выберите поставщика</option>
                {providers.map(p => (
                  <option key={p.providerId} value={p.providerId}>{p.providerName}</option>
                ))}
              </Form.Control>
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

      <Row className="report-body">
        <Col>
          <Table>
            <tbody>
              <tr>
                <td><b>Share Of Sale</b></td>
                <td style={{width: 400}}>{data.shareOfSale * 100}%</td>
              </tr>
              <tr>
                <td><b>Share Of Amount</b></td>
                <td style={{width: 400}}>{data.shareOfAmount * 100}%</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>

    </div>
  );
}
