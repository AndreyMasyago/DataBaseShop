import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { getFutureOrders } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function FutureOrders() {
  const [data, setData] = useState({
    count: [[]],
    results: []
  });

  const callback = formState => getFutureOrders().then(data => setData(data));

  const { formState } = useForm({
  }, callback);

  useEffect(() => { callback() }, [formState]);

  return (
    <div>
      <h1>Заявки на ожидаемый товар</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Order Date</th>
            <th>Order Price</th>
          </tr>
        </thead>
        <tbody>
          {data.results.map(item => (
            <tr key={item[0].orderId}>
              <td><Link to={`/orders/${item[0].orderId}/`}>{item[0].orderId}</Link></td>
              <td>{item[0].orderDate}</td>
              <td>{item[1]}</td>
            </tr>
          ))}

          <tr>
            <td><b>Количество:</b></td>
            <td><b>{data.count[0][1]}</b></td>
            <td><b>{data.count[0][0]}</b></td>
          </tr>
        </tbody>
      </Table>


    </div>
  );
}