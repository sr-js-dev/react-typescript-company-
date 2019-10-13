import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './risk-class.reducer';
import { IRiskClass } from 'app/shared/model/risk-class.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRiskClassDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RiskClassDetail extends React.Component<IRiskClassDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { riskClassEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RiskClass [<b>{riskClassEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{riskClassEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{riskClassEntity.description}</dd>
            <dt>Risk Category</dt>
            <dd>{riskClassEntity.riskCategoryId ? riskClassEntity.riskCategoryId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/risk-class" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/risk-class/${riskClassEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ riskClass }: IRootState) => ({
  riskClassEntity: riskClass.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskClassDetail);
