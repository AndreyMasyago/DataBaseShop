import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';

import { getGoodsDetails } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function GoodsDetails () {
  const [data, setData] = useState([]);

  const callback = formState => getGoodsDetails(formState).then(data => setData(data.results));

  const { handleInputChange, formState } = useForm({ goodsSearch: '' }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <h1>Top 5 best selling goods</h1>

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
            <th>Goods Name</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.goodsId}>
              <td><Link to={`/goods/${item.goodsId}/`}>{item.goodsId}</Link></td>
              <td>{item.goodsName}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}