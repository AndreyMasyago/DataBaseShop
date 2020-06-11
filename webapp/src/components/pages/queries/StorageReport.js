import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { getStorageReport } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function StorageReport() {
  const [data, setData] = useState([]);

  const callback = formState => getStorageReport().then(data => setData(data.results));

  const { formState } = useForm({
  }, callback);

  useEffect(() => { callback() }, [formState]);

  console.log(data);

  return (
    <div>
      <h1>Инвентаризационная ведомость</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Goods Name</th>
            <th>Amount</th>
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
              <td>{item.producer}</td>
              <td>{item.providerName}</td>
            </tr>
          ))}
        </tbody>
      </Table>


    </div>
  );
}