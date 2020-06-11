import React, { useState, useEffect } from 'react';
import { Link, useRouteMatch } from 'react-router-dom';
import Table from 'react-bootstrap/Table';

import { listCells, deleteCell } from '../../../api/storage';

export default function StorageList() {
  const [data, setData] = useState([]);

  useEffect(() => { listCells().then(setData); }, []);

  function deleteItem(itemId) {
    deleteCell(itemId)
      .then(listCells)
      .then(setData);
  }

  const match = useRouteMatch();

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Cells Size</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {data.map(item => (
          <tr key={item.cellsId}>
            <td><Link to={`${match.url}${item.cellsId}/`}>{item.cellsId}</Link></td>
            <td>{item.cellsSize}</td>
            <td style={{width: 140, textAlign: 'center'}}><span className="btn btn-danger" onClick={() => deleteItem(item.cellsId)}>Удалить</span></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
