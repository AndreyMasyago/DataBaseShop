import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getFreeSpace } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function FreeSpace() {
  const [data, setData] = useState({
    freeSpace: 0,
    results: []
  });

  const callback = formState => getFreeSpace().then(data => setData(data));

  const { formState } = useForm({
  }, callback);

  useEffect(() => { callback() }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Свободное пространство</h1>
        </Col>
      </Row>

      <Row className="report-body">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#Cells Id</th>
                <th>Cells size</th>
                <th>Free Space</th>
              </tr>
            </thead>
            <tbody>
              {data.results.map(item => (
                <tr key={item[0].cellsId}>
                  <td><Link to={`/storage/${item[0].cellsId}/`}>{item[0].cellsId}</Link></td>
                  <td>{item[0].cellsSize}</td>
                  <td>{item[1]}</td>
                </tr>
              ))}

              <tr>
                <td></td>
                <td>Total:</td>
                <td>{data.freeSpace}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>

    </div>
  );
}
