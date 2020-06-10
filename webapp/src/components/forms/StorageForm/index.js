import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { createCell, getCell, updateCell } from '../../../api/storage';

import useForm from '../formHook';

import './styles.css';

function StorageForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const callback = (data) => {
    const dataToSave = {
      ...data
    };

    if (update) {
      return updateCell(dataToSave)
    } else {
      return createCell(dataToSave);
    }
  }
  
  const { formState, handleSubmit, handleInputChange, setFormState } = useForm(
    {cellsSize: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getCell(match.params.id).then((data) => {
      setFormState({
        ...data
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/storage/');
  }

  return (
    <div className="StorageForm">
      <Form onSubmit={handleSubmit}>
        <Form.Label>Склад и его ячейки</Form.Label>
        <Form.Group>
          <Form.Label>Размер ячейки в литрах</Form.Label>
          <Form.Control 
            type="number" 
            min="1" 
            placeholder="Введите размер ячейки" 
            name="cellsSize"
            value={formState.cellsSize}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Button variant="outline-success" type="submit">
          {update ? 'Сохранить изменения' : 'Добавить ячейку'}          
        </Button>
      </Form>
    </div>
  );
}

export default StorageForm;
