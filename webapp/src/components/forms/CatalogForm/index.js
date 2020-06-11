import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { createDetail, getDetail, updateDetail } from '../../../api/catalog';

import useForm from '../formHook';

import './styles.css';

function CatalogForm() {
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
      return updateDetail(dataToSave)
    } else {
      return createDetail(dataToSave);
    }
  }

  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    goodsName: ""
  }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getDetail(match.params.id).then((data) => {
      setFormState({
        ...data
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/catalog/');
  }

  return (
    <Row>
      <Col md={{ span: 6, offset: 3 }}>
        <h2>{update ? 'Изменить' : 'Добавить'} деталь</h2>

        <Form onSubmit={handleSubmit}>

          <Form.Group>
            <Form.Label>Название детали</Form.Label>
            <Form.Control
              type="text"
              placeholder="Введите название детали"
              name="goodsName"
              value={formState.goodsName}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Button variant="outline-success" type="submit">
            {update ? 'Сохранить изменения' : 'Добавить деталь'}
          </Button>

        </Form>
      </Col>
    </Row>
  );
}

export default CatalogForm;
