import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './underwriter.reducer';
import { IUnderwriter } from 'app/shared/model/underwriter.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUnderwriterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UnderwriterDetail extends React.Component<IUnderwriterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { underwriterEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Underwriter [<b>{underwriterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{underwriterEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{underwriterEntity.description}</dd>
            <dt>
              <span id="logo">Logo</span>
            </dt>
            <dd>{underwriterEntity.logo}</dd>
            <dt>
              <span id="binder">Binder</span>
            </dt>
            <dd>{underwriterEntity.binder}</dd>
            <dt>
              <span id="website">Website</span>
            </dt>
            <dd>{underwriterEntity.website}</dd>
            <dt>
              <span id="contactPersion">Contact Persion</span>
            </dt>
            <dd>{underwriterEntity.contactPersion}</dd>
            <dt>
              <span id="telephone">Telephone</span>
            </dt>
            <dd>{underwriterEntity.telephone}</dd>
            <dt>
              <span id="mobile">Mobile</span>
            </dt>
            <dd>{underwriterEntity.mobile}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{underwriterEntity.email}</dd>
            <dt>
              <span id="address">Address</span>
            </dt>
            <dd>{underwriterEntity.address}</dd>
            <dt>
              <span id="streetAddress">Street Address</span>
            </dt>
            <dd>{underwriterEntity.streetAddress}</dd>
            <dt>
              <span id="county">County</span>
            </dt>
            <dd>{underwriterEntity.county}</dd>
            <dt>
              <span id="country">Country</span>
            </dt>
            <dd>{underwriterEntity.country}</dd>
            <dt>
              <span id="pinNumber">Pin Number</span>
            </dt>
            <dd>{underwriterEntity.pinNumber}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{underwriterEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/underwriter" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/underwriter/${underwriterEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ underwriter }: IRootState) => ({
  underwriterEntity: underwriter.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UnderwriterDetail);
