import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

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
      <Row>
        <Col>
          <h1>Заявки на ожидаемый товар</h1>
        </Col>
      </Row>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#Cells Id</th>
                <th>Free Space</th>
                <th>Cells size</th>
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

              <tr >
                <td>Количество:</td>
                <td>{data.count[0][1]}</td>
                <td>{data.count[0][0]}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
