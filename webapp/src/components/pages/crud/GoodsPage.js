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

import GoodsForm from '../../forms/GoodsForm';
import GoodsList from '../../lists/GoodsList';

export default function GoodsPage() {
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
                  Goods
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Create</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <GoodsForm />
        </Route>

        <Route path={`${match.path}:id/`}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active><Link to={`${match.url}/`}>
                  Goods
                </Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Edit</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <GoodsForm />
        </Route>

        <Route path={match.path}>
          <Row>
            <Col>
              <Breadcrumb>
                <Breadcrumb.Item active><Link to="/">Home</Link></Breadcrumb.Item>
                <Breadcrumb.Item active>Goods</Breadcrumb.Item>
              </Breadcrumb>
            </Col>
          </Row>

          <Row className="btn-create-container">
            <Col>
              <Link className="btn btn-primary" to={`${match.url}create/`}>Create goods</Link>
            </Col>
          </Row>

          <GoodsList />
        </Route>
      </Switch>
    </div>
  );
}
