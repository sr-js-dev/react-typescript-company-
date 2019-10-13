import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './benefit-type.reducer';
import { IBenefitType } from 'app/shared/model/benefit-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBenefitTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BenefitTypeDetail extends React.Component<IBenefitTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { benefitTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BenefitType [<b>{benefitTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{benefitTypeEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{benefitTypeEntity.description}</dd>
          </dl>
          <Button tag={Link} to="/entity/benefit-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/benefit-type/${benefitTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ benefitType }: IRootState) => ({
  benefitTypeEntity: benefitType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BenefitTypeDetail);
