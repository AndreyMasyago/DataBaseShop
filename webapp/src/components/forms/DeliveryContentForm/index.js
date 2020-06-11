import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { listDeliveries } from '../../../api/delivery';
import { listGoods } from '../../../api/goods';
import { createDeliveryContent, getDeliveryContent, updateDeliveryContent } from '../../../api/deliveryContent';

import useForm from '../formHook';

import './styles.css';

function DeliveryContentForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const [deliveries, setDeliveries] = useState([]);
  useEffect(() => { listDeliveries().then(setDeliveries); }, []);

  const [goods, setGoods] = useState([]);
  useEffect(() => { listGoods().then(setGoods); }, []);

  const callback = (data) => {
    const dataToSave = {
      ...data,
      delivery: {
        deliveryId: data.deliveryId
      },
      goods: {
        goodsId: data.goodsId
      }
    };

    if (update) {
      return updateDeliveryContent(dataToSave)
    } else {
      return createDeliveryContent(dataToSave);
    }
  }

  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    deliveryId: "",
    amount: "",
    goodsId: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getDeliveryContent(match.params.id).then((data) => {
      setFormState({
        ...data,
        deliveryId: data.delivery.deliveryId,
        goodsId: data.goods.goodsId
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/deliveryContent/');
  }

  return (
    <Row>
      <Col md={{ span: 6, offset: 3 }}>
        <h2>{update ? 'Изменить' : 'Добавить'} содержимое поставки</h2>

        <Form onSubmit={handleSubmit}>

          <Form.Group as={Form.Col}>
            <Form.Label>Номер и дата поставки</Form.Label>
            <Form.Control
              as="select"
              name="deliveryId"
              value={formState.deliveryId}
              onChange={handleInputChange}
              required
            >
              <option value="" disabled>Выберите поставку</option>
              {deliveries.map (d => (
                <option key={d.deliveryId} value={d.deliveryId}>
                  {`${d.deliveryId} ${d.arrivingDateOnStorage}`}
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
                  {g.catalog.goodsName} {g.producer} {g.provider.providerName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>

          <Button variant="outline-success" type="submit">
            {update ? 'Сохранить изменения' : 'Добавить содержимое поставки'}
          </Button>
        </Form>
      </Col>
    </Row>
  );
}

export default DeliveryContentForm;
