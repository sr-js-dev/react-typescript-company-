import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './policy.reducer';
import { IPolicy } from 'app/shared/model/policy.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPolicyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PolicyDetail extends React.Component<IPolicyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { policyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Policy [<b>{policyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{policyEntity.name}</dd>
            <dt>
              <span id="primiumPayable">Primium Payable</span>
            </dt>
            <dd>{policyEntity.primiumPayable}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{policyEntity.status}</dd>
            <dt>Cover Type</dt>
            <dd>{policyEntity.coverTypeId ? policyEntity.coverTypeId : ''}</dd>
            <dt>Underwriter</dt>
            <dd>{policyEntity.underwriterId ? policyEntity.underwriterId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/policy" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/policy/${policyEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ policy }: IRootState) => ({
  policyEntity: policy.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PolicyDetail);
