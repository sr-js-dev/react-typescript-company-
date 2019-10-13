import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './risk-category.reducer';
import { IRiskCategory } from 'app/shared/model/risk-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRiskCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RiskCategoryDetail extends React.Component<IRiskCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { riskCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RiskCategory [<b>{riskCategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{riskCategoryEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{riskCategoryEntity.description}</dd>
            <dt>Product Type</dt>
            <dd>{riskCategoryEntity.productTypeId ? riskCategoryEntity.productTypeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/risk-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/risk-category/${riskCategoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ riskCategory }: IRootState) => ({
  riskCategoryEntity: riskCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskCategoryDetail);
