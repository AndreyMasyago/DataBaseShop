import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

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
      <h1>Свободное пространство</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Cells Size</th>
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
            <td><b>Total:</b></td>
            <td><b>{data.freeSpace}</b></td>
          </tr>
        </tbody>
      </Table>


    </div>
  );
}