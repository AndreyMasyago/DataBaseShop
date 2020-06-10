import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { listDetails } from '../../../api/catalog';
import { listProviders } from '../../../api/provider';
import { createGoods, getGoods, updateGoods } from '../../../api/goods';

import useForm from '../formHook';

import './styles.css';

function GoodsForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const [catalog, setCatalog] = useState([]);
  useEffect(() => { listDetails().then(setCatalog); }, []);

  const [providers, setProviders] = useState([]);
  useEffect(() => { listProviders().then(setProviders); }, []);

  const callback = (data) => {
    const dataToSave = {
      ...data,
      catalog: {
        detailId: data.detailId
      },
      provider: {
        providerId: data.providerId
      }
    };

    if (update) {
      return updateGoods(dataToSave)
    } else {
      return createGoods(dataToSave);
    }
  }
  
  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    detailId: "",
    size: "",
    deliveryTime: "",
    purchasePrice: "",
    sellingPrice: "",
    producer: "",
    providerId: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getGoods(match.params.id).then((data) => {
      setFormState({
        ...data,
        detailId: data.catalog.detailId,
        providerId: data.provider.providerId
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/goods/');
  }

  return (
    <div className="GoodsForm">
      <Form onSubmit={handleSubmit}>
        <Form.Label>Каталог товаров</Form.Label>
        <Form.Group as={Form.Col}>
          <Form.Label>Наименование детали</Form.Label>
          <Form.Control 
            as="select" 
            name="detailId" 
            value={formState.detailId} 
            onChange={handleInputChange}
            required
          >
            <option value="" disabled>Выберите деталь</option>
            {catalog.map(c => (
              <option key={c.detailId} value={c.detailId}>{c.goodsName}</option>
            ))}
          </Form.Control>
        </Form.Group>

        <Form.Group>
          <Form.Label>Размер товара в см3</Form.Label>
          <Form.Control 
            type="number" 
            min="1" 
            placeholder="Введите размер товара" 
            name="size"
            value={formState.size}
            onChange={handleInputChange}
            required
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>Среднее время поставки</Form.Label>
          <Form.Control
            type="number" 
            min = "1" 
            placeholder="Введите время поставки" 
            name="deliveryTime"
            value={formState.deliveryTime}
            onChange={handleInputChange}
            required
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>Цена покупки</Form.Label>
          <Form.Control 
            type="number" 
            min = "1" 
            placeholder="Введите цену покупки" 
            name="purchasePrice"
            value={formState.purchasePrice}
            onChange={handleInputChange}
            required
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>Цена продажи</Form.Label>
          <Form.Control
            type="number"
            min="1"
            placeholder="Введите цену продажи" 
            name="sellingPrice"
            value={formState.sellingPrice}
            onChange={handleInputChange}
            required
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>Производитель</Form.Label>
          <Form.Control
            type="text"
            placeholder="Введите наименование производителя" 
            name="producer"
            value={formState.producer}
            onChange={handleInputChange}
            required
          />
        </Form.Group>

        <Form.Group as={Form.Col}>
          <Form.Label>Поставщик</Form.Label>
          <Form.Control
            as="select" 
            name="providerId" 
            value={formState.providerId} 
            onChange={handleInputChange}
            required
          >
            <option value="" disabled>Выберите поставщика</option>
            {providers.map(p => (
              <option key={p.providerId} value={p.providerId}>{p.providerName}</option>
            ))}
          </Form.Control>
        </Form.Group>
        
        <Button variant="outline-success" type="submit">
          {update ? 'Сохранить изменения' : 'Добавить товар'}
        </Button>

      </Form>
    </div>
  );
}

export default GoodsForm;
