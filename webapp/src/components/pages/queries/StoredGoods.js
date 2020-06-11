import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getStoredGoods } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function StorageReport() {
  const [data, setData] = useState([]);

  const callback = formState => getStoredGoods().then(data => setData(data.results));

  const { formState } = useForm({
  }, callback);

  useEffect(() => { callback() }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Инвентаризационная ведомость</h1>
        </Col>
      </Row>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Goods Name</th>
                <th>Total Amount</th>
                <th>Total Size</th>
                <th>Producer</th>
                <th>Provider</th>
                <th>Cells Id</th>
              </tr>
            </thead>
            <tbody>
              {data.map(item => (
                <tr key={item[0].goodsId}>
                  <td><Link to={`/goods/${item[0].goodsId}/`}>{item[0].catalog.goodsName}</Link></td>
                  <td>{item[2]}</td>
                  <td>{item[3]}</td>
                  <td>{item[0].producer}</td>
                  <td>{item[0].provider.providerName}</td>
                  <td>{item[1].cellsId}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>

    </div>
  );
}
