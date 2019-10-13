import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './underwriter.reducer';
import { IUnderwriter } from 'app/shared/model/underwriter.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUnderwriterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUnderwriterUpdateState {
  isNew: boolean;
}

export class UnderwriterUpdate extends React.Component<IUnderwriterUpdateProps, IUnderwriterUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { underwriterEntity } = this.props;
      const entity = {
        ...underwriterEntity,
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
    this.props.history.push('/entity/underwriter');
  };

  render() {
    const { underwriterEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.underwriter.home.createOrEditLabel">Create or edit a Underwriter</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : underwriterEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="underwriter-id">ID</Label>
                    <AvInput id="underwriter-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="underwriter-name">
                    Name
                  </Label>
                  <AvField
                    id="underwriter-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="underwriter-description">
                    Description
                  </Label>
                  <AvField id="underwriter-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="logoLabel" for="underwriter-logo">
                    Logo
                  </Label>
                  <AvField id="underwriter-logo" type="text" name="logo" />
                </AvGroup>
                <AvGroup>
                  <Label id="binderLabel" for="underwriter-binder">
                    Binder
                  </Label>
                  <AvField id="underwriter-binder" type="text" name="binder" />
                </AvGroup>
                <AvGroup>
                  <Label id="websiteLabel" for="underwriter-website">
                    Website
                  </Label>
                  <AvField id="underwriter-website" type="text" name="website" />
                </AvGroup>
                <AvGroup>
                  <Label id="contactPersionLabel" for="underwriter-contactPersion">
                    Contact Persion
                  </Label>
                  <AvField id="underwriter-contactPersion" type="text" name="contactPersion" />
                </AvGroup>
                <AvGroup>
                  <Label id="telephoneLabel" for="underwriter-telephone">
                    Telephone
                  </Label>
                  <AvField id="underwriter-telephone" type="text" name="telephone" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="underwriter-mobile">
                    Mobile
                  </Label>
                  <AvField id="underwriter-mobile" type="text" name="mobile" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="underwriter-email">
                    Email
                  </Label>
                  <AvField id="underwriter-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="underwriter-address">
                    Address
                  </Label>
                  <AvField id="underwriter-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="streetAddressLabel" for="underwriter-streetAddress">
                    Street Address
                  </Label>
                  <AvField id="underwriter-streetAddress" type="text" name="streetAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="countyLabel" for="underwriter-county">
                    County
                  </Label>
                  <AvField id="underwriter-county" type="text" name="county" />
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="underwriter-country">
                    Country
                  </Label>
                  <AvField id="underwriter-country" type="text" name="country" />
                </AvGroup>
                <AvGroup>
                  <Label id="pinNumberLabel" for="underwriter-pinNumber">
                    Pin Number
                  </Label>
                  <AvField id="underwriter-pinNumber" type="string" className="form-control" name="pinNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="underwriter-status">
                    Status
                  </Label>
                  <AvInput
                    id="underwriter-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && underwriterEntity.status) || 'ACTIVE'}
                  >
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/underwriter" replace color="info">
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
  underwriterEntity: storeState.underwriter.entity,
  loading: storeState.underwriter.loading,
  updating: storeState.underwriter.updating,
  updateSuccess: storeState.underwriter.updateSuccess
});

const mapDispatchToProps = {
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
)(UnderwriterUpdate);
