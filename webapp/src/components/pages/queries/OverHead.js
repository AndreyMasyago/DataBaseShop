import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { getOverHead } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function OverHead () {
  const [data, setData] = useState([]);

  const callback = formState => getOverHead().then(data => setData(data.overhead));

  const { formState } = useForm({
    overhead: ''
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <Row>
        <Col>
          <h1>Накладные расходы</h1>
        </Col>
      </Row>

      <Row className="report-body">
        <Col>
          <Table>
            <tbody>
              <tr>
                <td><b>Накладные расходы (%)</b></td>
                <td style={{width: 400}}>{data}%</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}
