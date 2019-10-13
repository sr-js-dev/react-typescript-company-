import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-policy-payment.reducer';
import { IClientPolicyPayment } from 'app/shared/model/client-policy-payment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientPolicyPaymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientPolicyPaymentDetail extends React.Component<IClientPolicyPaymentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientPolicyPaymentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ClientPolicyPayment [<b>{clientPolicyPaymentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="payDate">Pay Date</span>
            </dt>
            <dd>
              <TextFormat value={clientPolicyPaymentEntity.payDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{clientPolicyPaymentEntity.amount}</dd>
            <dt>
              <span id="paymentMethod">Payment Method</span>
            </dt>
            <dd>{clientPolicyPaymentEntity.paymentMethod}</dd>
            <dt>
              <span id="isIPF">Is IPF</span>
            </dt>
            <dd>{clientPolicyPaymentEntity.isIPF ? 'true' : 'false'}</dd>
            <dt>Client Policy</dt>
            <dd>{clientPolicyPaymentEntity.clientPolicyId ? clientPolicyPaymentEntity.clientPolicyId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client-policy-payment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/client-policy-payment/${clientPolicyPaymentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ clientPolicyPayment }: IRootState) => ({
  clientPolicyPaymentEntity: clientPolicyPayment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientPolicyPaymentDetail);
