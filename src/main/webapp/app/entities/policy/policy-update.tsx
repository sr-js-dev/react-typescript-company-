import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverType } from 'app/shared/model/cover-type.model';
import { getEntities as getCoverTypes } from 'app/entities/cover-type/cover-type.reducer';
import { IUnderwriter } from 'app/shared/model/underwriter.model';
import { getEntities as getUnderwriters } from 'app/entities/underwriter/underwriter.reducer';
import { getEntity, updateEntity, createEntity, reset } from './policy.reducer';
import { IPolicy } from 'app/shared/model/policy.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPolicyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPolicyUpdateState {
  isNew: boolean;
  coverTypeId: string;
  underwriterId: string;
}

export class PolicyUpdate extends React.Component<IPolicyUpdateProps, IPolicyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      coverTypeId: '0',
      underwriterId: '0',
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

    this.props.getCoverTypes();
    this.props.getUnderwriters();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { policyEntity } = this.props;
      const entity = {
        ...policyEntity,
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
    this.props.history.push('/entity/policy');
  };

  render() {
    const { policyEntity, coverTypes, underwriters, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.policy.home.createOrEditLabel">Create or edit a Policy</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : policyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="policy-id">ID</Label>
                    <AvInput id="policy-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="policy-name">
                    Name
                  </Label>
                  <AvField
                    id="policy-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="primiumPayableLabel" for="policy-primiumPayable">
                    Primium Payable
                  </Label>
                  <AvField
                    id="policy-primiumPayable"
                    type="string"
                    className="form-control"
                    name="primiumPayable"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="policy-status">
                    Status
                  </Label>
                  <AvInput
                    id="policy-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && policyEntity.status) || 'ACTIVE'}
                  >
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="policy-coverType">Cover Type</Label>
                  <AvInput id="policy-coverType" type="select" className="form-control" name="coverTypeId" required>
                    {coverTypes
                      ? coverTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  <Label for="policy-underwriter">Underwriter</Label>
                  <AvInput id="policy-underwriter" type="select" className="form-control" name="underwriterId" required>
                    {underwriters
                      ? underwriters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/policy" replace color="info">
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
  coverTypes: storeState.coverType.entities,
  underwriters: storeState.underwriter.entities,
  policyEntity: storeState.policy.entity,
  loading: storeState.policy.loading,
  updating: storeState.policy.updating,
  updateSuccess: storeState.policy.updateSuccess
});

const mapDispatchToProps = {
  getCoverTypes,
  getUnderwriters,
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
)(PolicyUpdate);
