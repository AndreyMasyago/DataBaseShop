import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
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
      <h1>Top 5 best selling goods</h1>

      <Form className="search-form">
        <Form.Row>
          <Col className="col-lg-3">
            <Form.Control
              type="date"
              placeholder="С даты"
              name="startDate"
              value={formState.startDate}
              onChange={handleInputChange}
              required
            />
          </Col>

          <Col className="col-lg-3">
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

      <Table striped bordered hover variant="dark">
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
    </div>
  );
}
