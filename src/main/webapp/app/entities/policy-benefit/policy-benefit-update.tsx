import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBenefit } from 'app/shared/model/benefit.model';
import { getEntities as getBenefits } from 'app/entities/benefit/benefit.reducer';
import { IPolicy } from 'app/shared/model/policy.model';
import { getEntities as getPolicies } from 'app/entities/policy/policy.reducer';
import { getEntity, updateEntity, createEntity, reset } from './policy-benefit.reducer';
import { IPolicyBenefit } from 'app/shared/model/policy-benefit.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPolicyBenefitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPolicyBenefitUpdateState {
  isNew: boolean;
  benefitId: string;
  policyId: string;
}

export class PolicyBenefitUpdate extends React.Component<IPolicyBenefitUpdateProps, IPolicyBenefitUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      benefitId: '0',
      policyId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getBenefits();
    this.props.getPolicies();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { policyBenefitEntity } = this.props;
      const entity = {
        ...policyBenefitEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/policy-benefit');
  };

  render() {
    const { policyBenefitEntity, benefits, policies, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.policyBenefit.home.createOrEditLabel">Create or edit a PolicyBenefit</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : policyBenefitEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="policy-benefit-id">ID</Label>
                    <AvInput id="policy-benefit-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="benefitRateLabel" for="policy-benefit-benefitRate">
                    Benefit Rate
                  </Label>
                  <AvInput
                    id="policy-benefit-benefitRate"
                    type="select"
                    className="form-control"
                    name="benefitRate"
                    value={(!isNew && policyBenefitEntity.benefitRate) || 'FIXED'}
                  >
                    <option value="FIXED">FIXED</option>
                    <option value="PERCENTAGE">PERCENTAGE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="benefitValueLabel" for="policy-benefit-benefitValue">
                    Benefit Value
                  </Label>
                  <AvField id="policy-benefit-benefitValue" type="text" name="benefitValue" />
                </AvGroup>
                <AvGroup>
                  <Label id="benefitMinValueLabel" for="policy-benefit-benefitMinValue">
                    Benefit Min Value
                  </Label>
                  <AvField id="policy-benefit-benefitMinValue" type="string" className="form-control" name="benefitMinValue" />
                </AvGroup>
                <AvGroup>
                  <Label for="policy-benefit-benefit">Benefit</Label>
                  <AvInput id="policy-benefit-benefit" type="select" className="form-control" name="benefitId" required>
                    {benefits
                      ? benefits.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  <Label for="policy-benefit-policy">Policy</Label>
                  <AvInput id="policy-benefit-policy" type="select" className="form-control" name="policyId" required>
                    {policies
                      ? policies.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/policy-benefit" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  benefits: storeState.benefit.entities,
  policies: storeState.policy.entities,
  policyBenefitEntity: storeState.policyBenefit.entity,
  loading: storeState.policyBenefit.loading,
  updating: storeState.policyBenefit.updating,
  updateSuccess: storeState.policyBenefit.updateSuccess
});

const mapDispatchToProps = {
  getBenefits,
  getPolicies,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PolicyBenefitUpdate);
