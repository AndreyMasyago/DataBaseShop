import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { listOrderEntity } from '../../../api/orderEntity';
import { listGoods } from '../../../api/goods';
import { createReject, getReject, updateReject } from '../../../api/reject';

import useForm from '../formHook';

import './styles.css';

function RejectForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const [orderEntities, setOrderEntities] = useState([]);
  useEffect(() => { listOrderEntity().then(setOrderEntities); }, []);

  const [goods, setGoods] = useState([]);
  useEffect(() => { listGoods().then(setGoods); }, []);

  const callback = (data) => {
    const dataToSave = {
      ...data,
      goods: {
        goodsId: data.goodsId
      },
      orderEntity: {
        orderId: data.orderId
    }
  };

    if (update) {
      return updateReject(dataToSave)
    } else {
      return createReject(dataToSave);
    }
  }
 
  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    goodsId: "",
    amount: "",
    orderId: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getReject(match.params.id).then((data) => {
      setFormState({
        ...data,
        goodsId: data.goods.goodsId,
        orderId: data.orderEntity.orderId
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/reject/');
  }
  return (
    <div className="RejectForm">
      <Form onSubmit={handleSubmit}>
        <Form.Label>Содержимое возврата(брак)</Form.Label>

        <Form.Group as={Form.Col}>
          <Form.Label>Номер и дата заказа</Form.Label>
          <Form.Control 
            as="select" 
            name="orderId"
            value={formState.orderId}
            onChange={handleInputChange}
            required
          >
            <option value="" disabled>Выберите заказ</option>
            {orderEntities.map(o => (
              <option key={o.orderId} value={o.orderId}>
                {`${o.orderId} ${o.orderDate}`}
              </option>
            ))}
          </Form.Control>
        </Form.Group>

        <Form.Group as={Form.Col}>
          <Form.Label>Товар</Form.Label>
          <Form.Control 
            as="select" 
            name="goodsId"
            value={formState.goodsId}
            onChange={handleInputChange}
            required
          >
            <option value="" disabled>Выберите товар</option>
            {goods.map(g => (
              <option key={g.goodsId} value={g.goodsId}>
                {`${g.catalog.goodsName} ${g.producer} ${g.provider.providerName}`}
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Form.Group>
          <Form.Label>Количество товара</Form.Label>
          <Form.Control 
            type="number" 
            min="1" 
            placeholder="Введите количество товара"
            name="amount"
            value={formState.amount}
            onChange={handleInputChange}
            required 
          />
        </Form.Group>

        <Button variant="outline-success" type="submit">
          {update ? 'Сохранить изменения' : 'Добавить содержимое возврата(брак)'}
        </Button>
      </Form>
    </div>
  );
}

export default RejectForm;
