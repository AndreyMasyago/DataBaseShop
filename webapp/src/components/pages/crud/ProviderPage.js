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

import ProvidersForm from '../../forms/ProvidersForm';
import ProviderList from '../../lists/ProviderList';

export default function ProviderPage() {
  const match = useRouteMatch();

  return (
    <div>

      <Switch>
        <Route path={`${match.path}create/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item href="/">Home</Breadcrumb.Item>
                <Breadcrumb.Item href={`${match.url}/`}>
                  Provider
                </Breadcrumb.Item>
                <Breadcrumb.Item active>Create</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <ProvidersForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item href="/">Home</Breadcrumb.Item>
                <Breadcrumb.Item href={`${match.url}/`}>
                  Provider
                </Breadcrumb.Item>
                <Breadcrumb.Item active>Edit</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <ProvidersForm />
        </Route>

        <Route path={match.path}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item href="/">Home</Breadcrumb.Item>
                <Breadcrumb.Item active>Provider</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <Row className="btn-create-container">
            <Col>
              <Link className="btn btn-primary" to={`${match.url}create/`}>Create provider</Link>
            </Col>
          </Row>

          <ProviderList />
        </Route>
      </Switch>
    </div>
  );
}
