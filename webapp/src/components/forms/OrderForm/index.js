import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import useForm from '../formHook';

import { createOrderEntity, getOrderEntity, updateOrderEntity } from '../../../api/orderEntity';

import './styles.css';

function OrderForm() {
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
      return updateOrderEntity(dataToSave)
    } else {
      return createOrderEntity(dataToSave);
    }
  }
  
  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    orderDate: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getOrderEntity(match.params.id).then((data) => {
      setFormState({
        ...data
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/order/');
  }

  return (
    <div className="OrderForm">
      <Form onSubmit={handleSubmit}>
        <Form.Label>Заказы</Form.Label>
        <Form.Group>
          <Form.Label>Дата заказа</Form.Label>          
          <Form.Control 
            type="date" 
            placeholder="Выберите дату заказа" 
            name="orderDate"
            value={formState.orderDate}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Button variant="outline-success" type="submit">        
          {update ? 'Сохранить изменения' : 'Добавить заказ'}          
        </Button>
      </Form>
    </div>
  );
}

export default OrderForm;
