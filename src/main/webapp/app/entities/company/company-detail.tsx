import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CompanyDetail extends React.Component<ICompanyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { companyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Company [<b>{companyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="usertype">Usertype</span>
            </dt>
            <dd>{companyEntity.usertype}</dd>
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{companyEntity.firstName}</dd>
            <dt>
              <span id="middleName">Middle Name</span>
            </dt>
            <dd>{companyEntity.middleName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{companyEntity.lastName}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{companyEntity.name}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{companyEntity.email}</dd>
            <dt>
              <span id="displayName">Display Name</span>
            </dt>
            <dd>{companyEntity.displayName}</dd>
            <dt>
              <span id="logo">Logo</span>
            </dt>
            <dd>{companyEntity.logo}</dd>
            <dt>
              <span id="telephone">Telephone</span>
            </dt>
            <dd>{companyEntity.telephone}</dd>
            <dt>
              <span id="contactPersion">Contact Persion</span>
            </dt>
            <dd>{companyEntity.contactPersion}</dd>
            <dt>
              <span id="mobile">Mobile</span>
            </dt>
            <dd>{companyEntity.mobile}</dd>
            <dt>
              <span id="address">Address</span>
            </dt>
            <dd>{companyEntity.address}</dd>
            <dt>
              <span id="streetAddress">Street Address</span>
            </dt>
            <dd>{companyEntity.streetAddress}</dd>
            <dt>
              <span id="county">County</span>
            </dt>
            <dd>{companyEntity.county}</dd>
            <dt>
              <span id="country">Country</span>
            </dt>
            <dd>{companyEntity.country}</dd>
            <dt>
              <span id="pinNumber">Pin Number</span>
            </dt>
            <dd>{companyEntity.pinNumber}</dd>
          </dl>
          <Button tag={Link} to="/entity/company" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/company/${companyEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ company }: IRootState) => ({
  companyEntity: company.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CompanyDetail);
