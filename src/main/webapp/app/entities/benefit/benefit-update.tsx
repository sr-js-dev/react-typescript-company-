import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBenefitType } from 'app/shared/model/benefit-type.model';
import { getEntities as getBenefitTypes } from 'app/entities/benefit-type/benefit-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './benefit.reducer';
import { IBenefit } from 'app/shared/model/benefit.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBenefitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBenefitUpdateState {
  isNew: boolean;
  benefitTypeId: string;
}

export class BenefitUpdate extends React.Component<IBenefitUpdateProps, IBenefitUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      benefitTypeId: '0',
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

    this.props.getBenefitTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { benefitEntity } = this.props;
      const entity = {
        ...benefitEntity,
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
    this.props.history.push('/entity/benefit');
  };

  render() {
    const { benefitEntity, benefitTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.benefit.home.createOrEditLabel">Create or edit a Benefit</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : benefitEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="benefit-id">ID</Label>
                    <AvInput id="benefit-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="benefit-name">
                    Name
                  </Label>
                  <AvField
                    id="benefit-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="benefitRateLabel" for="benefit-benefitRate">
                    Benefit Rate
                  </Label>
                  <AvInput
                    id="benefit-benefitRate"
                    type="select"
                    className="form-control"
                    name="benefitRate"
                    value={(!isNew && benefitEntity.benefitRate) || 'FIXED'}
                  >
                    <option value="FIXED">FIXED</option>
                    <option value="PERCENTAGE">PERCENTAGE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="benefit-description">
                    Description
                  </Label>
                  <AvField id="benefit-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="benefit-benefitType">Benefit Type</Label>
                  <AvInput id="benefit-benefitType" type="select" className="form-control" name="benefitTypeId">
                    <option value="" key="0" />
                    {benefitTypes
                      ? benefitTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/benefit" replace color="info">
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
  benefitTypes: storeState.benefitType.entities,
  benefitEntity: storeState.benefit.entity,
  loading: storeState.benefit.loading,
  updating: storeState.benefit.updating,
  updateSuccess: storeState.benefit.updateSuccess
});

const mapDispatchToProps = {
  getBenefitTypes,
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
)(BenefitUpdate);
