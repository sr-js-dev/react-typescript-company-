import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cover-type.reducer';
import { ICoverType } from 'app/shared/model/cover-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoverTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CoverTypeDetail extends React.Component<ICoverTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { coverTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            CoverType [<b>{coverTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{coverTypeEntity.name}</dd>
            <dt>
              <span id="brokerCommission">Broker Commission</span>
            </dt>
            <dd>{coverTypeEntity.brokerCommission}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{coverTypeEntity.description}</dd>
            <dt>Risk Class</dt>
            <dd>{coverTypeEntity.riskClassId ? coverTypeEntity.riskClassId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cover-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cover-type/${coverTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ coverType }: IRootState) => ({
  coverTypeEntity: coverType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CoverTypeDetail);
