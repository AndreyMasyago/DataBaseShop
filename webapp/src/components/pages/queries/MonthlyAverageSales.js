import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';

import { getMonthlyAverageSales } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function MonthlyAverageSales () {
  const [data, setData] = useState([]);

  const callback = formState => getMonthlyAverageSales(formState).then(data => setData(data.results));

  const { handleInputChange, formState } = useForm({
    goodsSearch: '',
    amountLimit: ''
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <h1>Montly Average Sales</h1>

      <Form className="search-form">
        <Form.Row>
          <Col className="col-lg-3">
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

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Goods ID</th>
            <th>Month</th>
            <th><b>Amount</b></th>
            <th>Goods Name</th>
            <th>Producer</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={`${item.goodsId}_${item.month}`}>
              <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
              <td>{item.month}</td>
              <td>{item.amount}</td>
              <td>{item.goodsName}</td>
              <td>{item.producer}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}