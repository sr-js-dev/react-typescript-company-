import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientDetail extends React.Component<IClientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Client [<b>{clientEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{clientEntity.firstName}</dd>
            <dt>
              <span id="middleName">Middle Name</span>
            </dt>
            <dd>{clientEntity.middleName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{clientEntity.lastName}</dd>
            <dt>
              <span id="clientName">Client Name</span>
            </dt>
            <dd>{clientEntity.clientName}</dd>
            <dt>
              <span id="ledgerName">Ledger Name</span>
            </dt>
            <dd>{clientEntity.ledgerName}</dd>
            <dt>
              <span id="clientPrintName">Client Print Name</span>
            </dt>
            <dd>{clientEntity.clientPrintName}</dd>
            <dt>
              <span id="idNumber">Id Number</span>
            </dt>
            <dd>{clientEntity.idNumber}</dd>
            <dt>
              <span id="contactPersion">Contact Persion</span>
            </dt>
            <dd>{clientEntity.contactPersion}</dd>
            <dt>
              <span id="telephone">Telephone</span>
            </dt>
            <dd>{clientEntity.telephone}</dd>
            <dt>
              <span id="mobile">Mobile</span>
            </dt>
            <dd>{clientEntity.mobile}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{clientEntity.email}</dd>
            <dt>
              <span id="address">Address</span>
            </dt>
            <dd>{clientEntity.address}</dd>
            <dt>
              <span id="streetAddress">Street Address</span>
            </dt>
            <dd>{clientEntity.streetAddress}</dd>
            <dt>
              <span id="county">County</span>
            </dt>
            <dd>{clientEntity.county}</dd>
            <dt>
              <span id="country">Country</span>
            </dt>
            <dd>{clientEntity.country}</dd>
            <dt>
              <span id="pinNumber">Pin Number</span>
            </dt>
            <dd>{clientEntity.pinNumber}</dd>
            <dt>
              <span id="notes">Notes</span>
            </dt>
            <dd>{clientEntity.notes}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{clientEntity.status}</dd>
            <dt>Category</dt>
            <dd>{clientEntity.categoryId ? clientEntity.categoryId : ''}</dd>
            <dt>Title</dt>
            <dd>{clientEntity.titleId ? clientEntity.titleId : ''}</dd>
            <dt>Id Type</dt>
            <dd>{clientEntity.idTypeId ? clientEntity.idTypeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/client/${clientEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientDetail);
