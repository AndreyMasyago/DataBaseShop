import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { createDelivery, getDelivery, updateDelivery } from '../../../api/delivery';

import useForm from '../formHook';

import './styles.css';

function DeliveryForm() {
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
      return updateDelivery(dataToSave)
    } else {
      return createDelivery(dataToSave);
    }
  }

  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    arrivingDateOnStorage: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getDelivery(match.params.id).then((data) => {
      setFormState({
        ...data
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/delivery/');
  }
  return (
    <Row>
      <Col md={{ span: 6, offset: 3 }}>
        <h2>{update ? 'Изменить' : 'Добавить'} поставку</h2>

        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <Form.Label>Дата поставки</Form.Label>
            <Form.Control
              type="date"
              placeholder="Выберите дату поставки"
              name="arrivingDateOnStorage"
              value={formState.arrivingDateOnStorage}
              onChange={handleInputChange}
              required
            />
          </Form.Group>
          <Button variant="outline-success" type="submit">
            {update ? 'Сохранить изменения' : 'Добавить поставку'}
          </Button>
        </Form>
      </Col>
    </Row>
  );
}

export default DeliveryForm;
