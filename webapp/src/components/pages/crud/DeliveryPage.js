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

import DeliveryForm from '../../forms/DeliveryForm';
import DeliveryList from '../../lists/DeliveryList';

export default function DeliveryPage() {
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
                  Delivery
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Create</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <DeliveryForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active><Link to={`${match.url}/`}>
                  Delivery
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Edit</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <DeliveryForm />
        </Route>

        <Route path={match.path}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Delivery</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <Row className="btn-create-container">
            <Col>
              <Link className="btn btn-primary" to={`${match.url}create/`}>Create delivery</Link>
            </Col>
          </Row>

          <DeliveryList />
        </Route>
      </Switch>
    </div>
  );
}
