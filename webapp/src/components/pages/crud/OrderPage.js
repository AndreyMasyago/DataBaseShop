import React from "react";
import {
  Switch,
  Route,
  Link,
  useRouteMatch
} from "react-router-dom";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Breadcrumb from 'react-bootstrap/Breadcrumb';

import OrderForm from '../../forms/OrderForm';
import OrderEntityList from '../../lists/OrderEntityList';

export default function OrderPage() {
  const match = useRouteMatch();

  return (
    <div>

      <Switch>
        <Route path={`${match.path}create/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active><Link to={`${match.url}/`}>
                  Order
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Create</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <OrderForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active><Link to={`${match.url}/`}>
                  Order
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Edit</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <OrderForm />
        </Route>

        <Route path={match.path}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Order</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <Row className="btn-create-container">
            <Col>
              <Link className="btn btn-primary" to={`${match.url}create/`}>Create order</Link>
            </Col>
          </Row>

          <OrderEntityList />
        </Route>
      </Switch>
    </div>
  );
}
