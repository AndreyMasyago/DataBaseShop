import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { listCells } from '../../../api/storage';
import { listGoods } from '../../../api/goods';
import { createStorageTransaction, getStorageTransaction, updateStorageTransaction } from '../../../api/storageTransactions';

import useForm from '../formHook';

import './styles.css';

function StorageTransactionForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const [storage, setStorage] = useState([]);
  useEffect(() => { listCells().then(setStorage); }, []);

  const [goods, setGoods] = useState([]);
  useEffect(() => { listGoods().then(setGoods); }, []);

  const callback = (data) => {
    const dataToSave = {
    ...data,
    storage: {
      cellsId: data.cellsId
    },
    goods: {
      goodsId: data.goodsId
    }
    };

    if (update) {
      return updateStorageTransaction(dataToSave)
    } else {
      return createStorageTransaction(dataToSave);
    }
  }

  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    cellsId: "",
    amount: "",
    goodsId: "",
    transactionDate: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getStorageTransaction(match.params.id).then((data) => {
      setFormState({
        ...data,
        cellsId: data.storage.cellsId,
        goodsId: data.goods.goodsId
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/storageTransactions/');
  }

  return (
    <Row>
      <Col md={{ span: 6, offset: 3 }}>
        <h2>{update ? 'Изменить' : 'Добавить'} транзакцию по складу</h2>
        <Form onSubmit={handleSubmit}>

          <Form.Group as={Form.Col}>
            <Form.Label>Ячейка</Form.Label>
            <Form.Control
              as="select"
              name="cellsId"
              value={formState.cellsId}
              onChange={handleInputChange}
              required
            >
              <option value="" disabled>Выберите ячейку</option>
              {storage.map(s => (
                <option key={s.cellsId} value={s.cellsId}>
                  {`${s.cellsId} ${s.cellsSize}`}
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
              placeholder="Введите количество товара"
              name="amount"
              value={formState.amount}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Form.Group>
            <Form.Label>Дата транзакции</Form.Label>
            <Form.Control
              type="date"
              placeholder="Выберите дату транзакции"
              name="transactionDate"
              value={formState.transactionDate}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Button variant="outline-success" type="submit">
            {update ? 'Сохранить изменения' : 'Добавить транзакцию'}
          </Button>
        </Form>
      </Col>
    </Row>
  );
}

export default StorageTransactionForm;
