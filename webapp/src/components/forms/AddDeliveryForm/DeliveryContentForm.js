import React from 'react';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';

function DeliveryContentForm({ goods, formState, onChange, onDelete, errors }) {
  const handleInputChange = (event) => {
    event.persist();

    onChange({
      ...formState,
      [event.target.name]: event.target.value
    });
  }

  console.log(errors);

  return (
    <div>
      <Form.Row>
        <Col lg={5}>
          <Form.Label>Товар</Form.Label>
        </Col>


        <Col lg={5}>
          <Form.Label>Количество товара</Form.Label>
        </Col>
      </Form.Row>

      <Form.Row>
        <Col lg={5}>
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
        </Col>

        <Col lg={5}>
          <Form.Control
            type="number"
            placeholder="Введите количество товара"
            name="amount"
            value={formState.amount}
            onChange={handleInputChange}
            required
            isInvalid={errors && errors.amount}
          />
          {(errors && errors.amount) &&
            <Form.Control.Feedback type="invalid">
              {errors.amount}
            </Form.Control.Feedback>
          }
        </Col>

        <Col lg={2} style={{ textAlign: 'center' }}>
          <Button variant="outline-danger" type="button" onClick={onDelete}>Удалить</Button>
        </Col>
      </Form.Row>

      <hr />
    </div>
  );
}

export default DeliveryContentForm;
