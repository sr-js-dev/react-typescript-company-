import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClientPolicy } from 'app/shared/model/client-policy.model';
import { getEntities as getClientPolicies } from 'app/entities/client-policy/client-policy.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client-policy-payment.reducer';
import { IClientPolicyPayment } from 'app/shared/model/client-policy-payment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientPolicyPaymentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IClientPolicyPaymentUpdateState {
  isNew: boolean;
  clientPolicyId: string;
}

export class ClientPolicyPaymentUpdate extends React.Component<IClientPolicyPaymentUpdateProps, IClientPolicyPaymentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientPolicyId: '0',
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

    this.props.getClientPolicies();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { clientPolicyPaymentEntity } = this.props;
      const entity = {
        ...clientPolicyPaymentEntity,
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
    this.props.history.push('/entity/client-policy-payment');
  };

  render() {
    const { clientPolicyPaymentEntity, clientPolicies, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.clientPolicyPayment.home.createOrEditLabel">Create or edit a ClientPolicyPayment</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientPolicyPaymentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="client-policy-payment-id">ID</Label>
                    <AvInput id="client-policy-payment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="payDateLabel" for="client-policy-payment-payDate">
                    Pay Date
                  </Label>
                  <AvField id="client-policy-payment-payDate" type="date" className="form-control" name="payDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="amountLabel" for="client-policy-payment-amount">
                    Amount
                  </Label>
                  <AvField id="client-policy-payment-amount" type="string" className="form-control" name="amount" />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentMethodLabel" for="client-policy-payment-paymentMethod">
                    Payment Method
                  </Label>
                  <AvInput
                    id="client-policy-payment-paymentMethod"
                    type="select"
                    className="form-control"
                    name="paymentMethod"
                    value={(!isNew && clientPolicyPaymentEntity.paymentMethod) || 'CARD'}
                  >
                    <option value="CARD">CARD</option>
                    <option value="MPESA">MPESA</option>
                    <option value="CASH">CASH</option>
                    <option value="CHEQUE">CHEQUE</option>
                    <option value="BANK_TRANSFER">BANK_TRANSFER</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="isIPFLabel" check>
                    <AvInput id="client-policy-payment-isIPF" type="checkbox" className="form-control" name="isIPF" />
                    Is IPF
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="client-policy-payment-clientPolicy">Client Policy</Label>
                  <AvInput id="client-policy-payment-clientPolicy" type="select" className="form-control" name="clientPolicyId" required>
                    {clientPolicies
                      ? clientPolicies.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/client-policy-payment" replace color="info">
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
  clientPolicies: storeState.clientPolicy.entities,
  clientPolicyPaymentEntity: storeState.clientPolicyPayment.entity,
  loading: storeState.clientPolicyPayment.loading,
  updating: storeState.clientPolicyPayment.updating,
  updateSuccess: storeState.clientPolicyPayment.updateSuccess
});

const mapDispatchToProps = {
  getClientPolicies,
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
)(ClientPolicyPaymentUpdate);
