import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IPolicy } from 'app/shared/model/policy.model';
import { getEntities as getPolicies } from 'app/entities/policy/policy.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client-policy.reducer';
import { IClientPolicy } from 'app/shared/model/client-policy.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientPolicyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IClientPolicyUpdateState {
  isNew: boolean;
  clientId: string;
  policyId: string;
}

export class ClientPolicyUpdate extends React.Component<IClientPolicyUpdateProps, IClientPolicyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
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

    this.props.getClients();
    this.props.getPolicies();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { clientPolicyEntity } = this.props;
      const entity = {
        ...clientPolicyEntity,
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
    this.props.history.push('/entity/client-policy');
  };

  render() {
    const { clientPolicyEntity, clients, policies, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.clientPolicy.home.createOrEditLabel">Create or edit a ClientPolicy</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientPolicyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="client-policy-id">ID</Label>
                    <AvInput id="client-policy-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="policyDateLabel" for="client-policy-policyDate">
                    Policy Date
                  </Label>
                  <AvField
                    id="client-policy-policyDate"
                    type="date"
                    className="form-control"
                    name="policyDate"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="invoiceNoLabel" for="client-policy-invoiceNo">
                    Invoice No
                  </Label>
                  <AvField id="client-policy-invoiceNo" type="text" name="invoiceNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="client-policy-startDate">
                    Start Date
                  </Label>
                  <AvField id="client-policy-startDate" type="date" className="form-control" name="startDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="client-policy-endDate">
                    End Date
                  </Label>
                  <AvField id="client-policy-endDate" type="date" className="form-control" name="endDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="primiumPayableLabel" for="client-policy-primiumPayable">
                    Primium Payable
                  </Label>
                  <AvField
                    id="client-policy-primiumPayable"
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
                  <Label id="openPayableLabel" for="client-policy-openPayable">
                    Open Payable
                  </Label>
                  <AvField
                    id="client-policy-openPayable"
                    type="string"
                    className="form-control"
                    name="openPayable"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentStatusLabel" for="client-policy-paymentStatus">
                    Payment Status
                  </Label>
                  <AvInput
                    id="client-policy-paymentStatus"
                    type="select"
                    className="form-control"
                    name="paymentStatus"
                    value={(!isNew && clientPolicyEntity.paymentStatus) || 'UNPAID'}
                  >
                    <option value="UNPAID">UNPAID</option>
                    <option value="PARTIAL">PARTIAL</option>
                    <option value="PAID">PAID</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="client-policy-client">Client</Label>
                  <AvInput id="client-policy-client" type="select" className="form-control" name="clientId" required>
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  <Label for="client-policy-policy">Policy</Label>
                  <AvInput id="client-policy-policy" type="select" className="form-control" name="policyId" required>
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
                <Button tag={Link} id="cancel-save" to="/entity/client-policy" replace color="info">
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
  clients: storeState.client.entities,
  policies: storeState.policy.entities,
  clientPolicyEntity: storeState.clientPolicy.entity,
  loading: storeState.clientPolicy.loading,
  updating: storeState.clientPolicy.updating,
  updateSuccess: storeState.clientPolicy.updateSuccess
});

const mapDispatchToProps = {
  getClients,
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
)(ClientPolicyUpdate);
