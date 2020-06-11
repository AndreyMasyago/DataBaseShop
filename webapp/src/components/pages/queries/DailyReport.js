import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';

import { getDailyReport } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function DailyReport () {
  const [data, setData] = useState([]);

  const callback = formState => getDailyReport(formState).then(data => setData(data.results));

  const { handleInputChange, formState } = useForm({
    reportDate: '2020-10-21'
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <h1>Отчёт за день</h1>
      <Form>
        <Form.Control
              type="date"
              placeholder="Выберите дату для отчёта"
              name="reportDate"
              value={formState.reportDate}
              onChange={handleInputChange}
              required
        />
      </Form>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Goods Name</th>
            <th>Amount</th>
            <th>Total Profit</th>
            <th>Producer</th>
            <th>Provider</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.goodsId}>
              <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
              <td>{item.goodsName}</td>
              <td>{item.amount}</td>
              <td>{item.total}</td>
              <td>{item.producer}</td>
              <td>{item.providerName}</td>
            </tr>
          ))}
        </tbody>
      </Table>


    </div>
  );
}