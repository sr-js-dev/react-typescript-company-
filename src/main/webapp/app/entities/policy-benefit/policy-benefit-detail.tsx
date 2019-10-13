import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './policy-benefit.reducer';
import { IPolicyBenefit } from 'app/shared/model/policy-benefit.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPolicyBenefitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PolicyBenefitDetail extends React.Component<IPolicyBenefitDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { policyBenefitEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PolicyBenefit [<b>{policyBenefitEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="benefitRate">Benefit Rate</span>
            </dt>
            <dd>{policyBenefitEntity.benefitRate}</dd>
            <dt>
              <span id="benefitValue">Benefit Value</span>
            </dt>
            <dd>{policyBenefitEntity.benefitValue}</dd>
            <dt>
              <span id="benefitMinValue">Benefit Min Value</span>
            </dt>
            <dd>{policyBenefitEntity.benefitMinValue}</dd>
            <dt>Benefit</dt>
            <dd>{policyBenefitEntity.benefitId ? policyBenefitEntity.benefitId : ''}</dd>
            <dt>Policy</dt>
            <dd>{policyBenefitEntity.policyId ? policyBenefitEntity.policyId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/policy-benefit" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/policy-benefit/${policyBenefitEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ policyBenefit }: IRootState) => ({
  policyBenefitEntity: policyBenefit.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PolicyBenefitDetail);
