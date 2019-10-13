import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRiskClass } from 'app/shared/model/risk-class.model';
import { getEntities as getRiskClasses } from 'app/entities/risk-class/risk-class.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cover-type.reducer';
import { ICoverType } from 'app/shared/model/cover-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoverTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICoverTypeUpdateState {
  isNew: boolean;
  riskClassId: string;
}

export class CoverTypeUpdate extends React.Component<ICoverTypeUpdateProps, ICoverTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      riskClassId: '0',
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

    this.props.getRiskClasses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { coverTypeEntity } = this.props;
      const entity = {
        ...coverTypeEntity,
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
    this.props.history.push('/entity/cover-type');
  };

  render() {
    const { coverTypeEntity, riskClasses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.coverType.home.createOrEditLabel">Create or edit a CoverType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : coverTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="cover-type-id">ID</Label>
                    <AvInput id="cover-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="cover-type-name">
                    Name
                  </Label>
                  <AvField
                    id="cover-type-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="brokerCommissionLabel" for="cover-type-brokerCommission">
                    Broker Commission
                  </Label>
                  <AvField
                    id="cover-type-brokerCommission"
                    type="string"
                    className="form-control"
                    name="brokerCommission"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="cover-type-description">
                    Description
                  </Label>
                  <AvField id="cover-type-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="cover-type-riskClass">Risk Class</Label>
                  <AvInput id="cover-type-riskClass" type="select" className="form-control" name="riskClassId" required>
                    {riskClasses
                      ? riskClasses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/cover-type" replace color="info">
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
  riskClasses: storeState.riskClass.entities,
  coverTypeEntity: storeState.coverType.entity,
  loading: storeState.coverType.loading,
  updating: storeState.coverType.updating,
  updateSuccess: storeState.coverType.updateSuccess
});

const mapDispatchToProps = {
  getRiskClasses,
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
)(CoverTypeUpdate);
