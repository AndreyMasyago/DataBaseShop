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

import DeliveryContentForm from '../../forms/DeliveryContentForm';
import DeliveryContentList from '../../lists/DeliveryContentList';

export default function DeliveryContentPage() {
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
                  Delivery Content
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Create</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <DeliveryContentForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active><Link to={`${match.url}/`}>
                  Delivery Content
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Edit</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <DeliveryContentForm />
        </Route>

        <Route path={match.path}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Delivery Content</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <Row className="btn-create-container">
            <Col>
              <Link className="btn btn-primary" to={`${match.url}create/`}>Create delivery content</Link>
            </Col>
          </Row>

          <DeliveryContentList />
        </Route>
      </Switch>
    </div>
  );
}
