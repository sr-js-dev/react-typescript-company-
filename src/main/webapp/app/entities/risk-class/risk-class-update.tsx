import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRiskCategory } from 'app/shared/model/risk-category.model';
import { getEntities as getRiskCategories } from 'app/entities/risk-category/risk-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './risk-class.reducer';
import { IRiskClass } from 'app/shared/model/risk-class.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRiskClassUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRiskClassUpdateState {
  isNew: boolean;
  riskCategoryId: string;
}

export class RiskClassUpdate extends React.Component<IRiskClassUpdateProps, IRiskClassUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      riskCategoryId: '0',
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

    this.props.getRiskCategories();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { riskClassEntity } = this.props;
      const entity = {
        ...riskClassEntity,
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
    this.props.history.push('/entity/risk-class');
  };

  render() {
    const { riskClassEntity, riskCategories, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.riskClass.home.createOrEditLabel">Create or edit a RiskClass</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : riskClassEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="risk-class-id">ID</Label>
                    <AvInput id="risk-class-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="risk-class-name">
                    Name
                  </Label>
                  <AvField
                    id="risk-class-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="risk-class-description">
                    Description
                  </Label>
                  <AvField id="risk-class-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="risk-class-riskCategory">Risk Category</Label>
                  <AvInput id="risk-class-riskCategory" type="select" className="form-control" name="riskCategoryId" required>
                    {riskCategories
                      ? riskCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/risk-class" replace color="info">
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
  riskCategories: storeState.riskCategory.entities,
  riskClassEntity: storeState.riskClass.entity,
  loading: storeState.riskClass.loading,
  updating: storeState.riskClass.updating,
  updateSuccess: storeState.riskClass.updateSuccess
});

const mapDispatchToProps = {
  getRiskCategories,
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
)(RiskClassUpdate);
