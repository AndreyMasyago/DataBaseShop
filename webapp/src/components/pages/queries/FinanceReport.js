import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getFinanceReport } from '../../../api/queries';
import useForm from '../../forms/formHook';

export default function FinanceReport () {
  const [data, setData] = useState([]);

  const callback = formState => getFinanceReport(formState).then(data => setData(data.results));

  const { handleInputChange, formState } = useForm({ startDate: '2020-10-01', endDate: '2020-11-01' }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Финансовый отчёт</h1>
        </Col>
      </Row>

      <Form className="search-form">
        <Form.Row>
          <Col lg={1}><Form.Label>С даты</Form.Label></Col>
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

          <Col lg={{span: 1, offset: 1}}><Form.Label>По дату</Form.Label></Col>
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

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Date</th>
                <th>Income</th>
                <th>Expenses</th>
                <th>Rejects</th>
                <th>Day total</th>
              </tr>
            </thead>
            <tbody>
              {data.map(item => (
                <tr key={item.date || 'Total'} className={!!item.date ? '' : 'FinanceReport-total-row'}>
                  <td>{item.date || 'Total'}</td>
                  <td>{item.income}</td>
                  <td>{item.expense}</td>
                  <td>{item.rejects}</td>
                  <td className="FinanceReport-total-col">{item.rowTotal}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
