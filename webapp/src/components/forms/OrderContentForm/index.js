import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { listOrderEntity } from '../../../api/orderEntity';
import { listGoods } from '../../../api/goods';
import { createOrderContent, getOrderContent, updateOrderContent } from '../../../api/orderContent';

import useForm from '../formHook';

import './styles.css';

function OrderContentForm() {
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
      return updateOrderContent(dataToSave)
    } else {
      return createOrderContent(dataToSave);
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

    getOrderContent(match.params.id).then((data) => {
      setFormState({
        ...data,
        goodsId: data.goods.goodsId,
        orderId: data.orderEntity.orderId
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/orderContent/');
  }

  return (
    <Row>
      <Col md={{ span: 6, offset: 3 }}>
        <h2>{update ? 'Изменить' : 'Добавить'} содержимое заказа</h2>

        <Form onSubmit={handleSubmit}>

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
            {update ? 'Сохранить изменения' : 'Добавить содержимое заказа'}
          </Button>
        </Form>
      </Col>
    </Row>
  );
}

export default OrderContentForm;
