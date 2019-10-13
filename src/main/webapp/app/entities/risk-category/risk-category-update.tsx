import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductType } from 'app/shared/model/product-type.model';
import { getEntities as getProductTypes } from 'app/entities/product-type/product-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './risk-category.reducer';
import { IRiskCategory } from 'app/shared/model/risk-category.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRiskCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRiskCategoryUpdateState {
  isNew: boolean;
  productTypeId: string;
}

export class RiskCategoryUpdate extends React.Component<IRiskCategoryUpdateProps, IRiskCategoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productTypeId: '0',
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

    this.props.getProductTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { riskCategoryEntity } = this.props;
      const entity = {
        ...riskCategoryEntity,
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
    this.props.history.push('/entity/risk-category');
  };

  render() {
    const { riskCategoryEntity, productTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.riskCategory.home.createOrEditLabel">Create or edit a RiskCategory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : riskCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="risk-category-id">ID</Label>
                    <AvInput id="risk-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="risk-category-name">
                    Name
                  </Label>
                  <AvField
                    id="risk-category-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="risk-category-description">
                    Description
                  </Label>
                  <AvField id="risk-category-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="risk-category-productType">Product Type</Label>
                  <AvInput id="risk-category-productType" type="select" className="form-control" name="productTypeId" required>
                    {productTypes
                      ? productTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/risk-category" replace color="info">
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
  productTypes: storeState.productType.entities,
  riskCategoryEntity: storeState.riskCategory.entity,
  loading: storeState.riskCategory.loading,
  updating: storeState.riskCategory.updating,
  updateSuccess: storeState.riskCategory.updateSuccess
});

const mapDispatchToProps = {
  getProductTypes,
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
)(RiskCategoryUpdate);
