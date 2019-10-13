import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-policy.reducer';
import { IClientPolicy } from 'app/shared/model/client-policy.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientPolicyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientPolicyDetail extends React.Component<IClientPolicyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientPolicyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ClientPolicy [<b>{clientPolicyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="policyDate">Policy Date</span>
            </dt>
            <dd>
              <TextFormat value={clientPolicyEntity.policyDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="invoiceNo">Invoice No</span>
            </dt>
            <dd>{clientPolicyEntity.invoiceNo}</dd>
            <dt>
              <span id="startDate">Start Date</span>
            </dt>
            <dd>
              <TextFormat value={clientPolicyEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">End Date</span>
            </dt>
            <dd>
              <TextFormat value={clientPolicyEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="primiumPayable">Primium Payable</span>
            </dt>
            <dd>{clientPolicyEntity.primiumPayable}</dd>
            <dt>
              <span id="openPayable">Open Payable</span>
            </dt>
            <dd>{clientPolicyEntity.openPayable}</dd>
            <dt>
              <span id="paymentStatus">Payment Status</span>
            </dt>
            <dd>{clientPolicyEntity.paymentStatus}</dd>
            <dt>Client</dt>
            <dd>{clientPolicyEntity.clientId ? clientPolicyEntity.clientId : ''}</dd>
            <dt>Policy</dt>
            <dd>{clientPolicyEntity.policyId ? clientPolicyEntity.policyId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client-policy" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/client-policy/${clientPolicyEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ clientPolicy }: IRootState) => ({
  clientPolicyEntity: clientPolicy.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientPolicyDetail);
