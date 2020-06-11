import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import useForm from '../formHook';
import { listGoods } from '../../../api/goods';
import { addDelivery } from '../../../api/queries';
import DeliveryContentForm from './DeliveryContentForm';

const EMPTY_STATE = {
  arrivingDateOnStorage: "",
  content: []
};

const EMPTY_CONTENT_STATE = {
  amount: "",
  goodsId: ""
};

function AddDeliveryForm({ saveFormState, lastFormState }) {
  const [goods, setGoods] = useState([]);
  useEffect(() => { listGoods().then(setGoods); }, []);

  const [errors, setErrors] = useState({});

  const callback = (data) => {
    setErrors({});

    return addDelivery(data).then(response => {
      if (response.errors) {
        setErrors(response.errors);
        return { preventRedirect: true };
      } else {
        saveFormState({});
      }
    });
  }

  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    ...EMPTY_STATE,
    ...lastFormState
  }, callback);

  useEffect(() => {
    saveFormState(formState);
  }, [saveFormState, formState])

  function onContentStateChange(i, newState) {
    const newContent = [...formState.content];
    if (newState) {
      newContent.splice(i, 1, newState);
    } else {
      newContent.splice(i, 1);
    }

    setFormState({
      ...formState,
      content: newContent
    });
  }

  function onAddState() {
    const newContent = [...formState.content, { ...EMPTY_CONTENT_STATE }];

    setFormState({
      ...formState,
      content: newContent
    });
  }

  const history = useHistory();

  if (formState.redirect) {
    history.push('/delivery/');
  }

  return (
    <Row>
      <Col md={{ span: 8, offset: 2 }}>
        <h2>Новая доставка</h2>

        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <Form.Label>Дата доставки</Form.Label>
            <Form.Control
              type="date"
              placeholder="Выберите дату доставки"
              name="arrivingDateOnStorage"
              value={formState.arrivingDateOnStorage}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <hr />
            {formState.content.map((contentState, i) => (
              <DeliveryContentForm
                key={i}
                goods={goods}
                formState={contentState}
                onChange={(newState) => onContentStateChange(i, newState)}
                onDelete={() => onContentStateChange(i)}
                errors={errors.content && errors.content[i]}
              />
            ))}

            <Button variant="outline-success" type="button" onClick={onAddState} style={{ marginTop: 20 }}>
              Добавить позицию
            </Button>
          <hr />

          <Button variant="outline-success" type="submit">
            Добавить доставку
          </Button>
        </Form>
      </Col>
    </Row>
  );
}

export default AddDeliveryForm;
