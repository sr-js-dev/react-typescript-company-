import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClientCategory } from 'app/shared/model/client-category.model';
import { getEntities as getClientCategories } from 'app/entities/client-category/client-category.reducer';
import { INameTitle } from 'app/shared/model/name-title.model';
import { getEntities as getNameTitles } from 'app/entities/name-title/name-title.reducer';
import { IIdType } from 'app/shared/model/id-type.model';
import { getEntities as getIdTypes } from 'app/entities/id-type/id-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IClientUpdateState {
  isNew: boolean;
  categoryId: string;
  titleId: string;
  idTypeId: string;
}

export class ClientUpdate extends React.Component<IClientUpdateProps, IClientUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: '0',
      titleId: '0',
      idTypeId: '0',
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

    this.props.getClientCategories();
    this.props.getNameTitles();
    this.props.getIdTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { clientEntity } = this.props;
      const entity = {
        ...clientEntity,
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
    this.props.history.push('/entity/client');
  };

  render() {
    const { clientEntity, clientCategories, nameTitles, idTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.client.home.createOrEditLabel">Create or edit a Client</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="client-id">ID</Label>
                    <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstNameLabel" for="client-firstName">
                    First Name
                  </Label>
                  <AvField id="client-firstName" type="text" name="firstName" />
                </AvGroup>
                <AvGroup>
                  <Label id="middleNameLabel" for="client-middleName">
                    Middle Name
                  </Label>
                  <AvField id="client-middleName" type="text" name="middleName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="client-lastName">
                    Last Name
                  </Label>
                  <AvField id="client-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientNameLabel" for="client-clientName">
                    Client Name
                  </Label>
                  <AvField id="client-clientName" type="text" name="clientName" />
                </AvGroup>
                <AvGroup>
                  <Label id="ledgerNameLabel" for="client-ledgerName">
                    Ledger Name
                  </Label>
                  <AvField id="client-ledgerName" type="text" name="ledgerName" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientPrintNameLabel" for="client-clientPrintName">
                    Client Print Name
                  </Label>
                  <AvField id="client-clientPrintName" type="text" name="clientPrintName" />
                </AvGroup>
                <AvGroup>
                  <Label id="idNumberLabel" for="client-idNumber">
                    Id Number
                  </Label>
                  <AvField id="client-idNumber" type="text" name="idNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="contactPersionLabel" for="client-contactPersion">
                    Contact Persion
                  </Label>
                  <AvField id="client-contactPersion" type="text" name="contactPersion" />
                </AvGroup>
                <AvGroup>
                  <Label id="telephoneLabel" for="client-telephone">
                    Telephone
                  </Label>
                  <AvField id="client-telephone" type="text" name="telephone" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="client-mobile">
                    Mobile
                  </Label>
                  <AvField id="client-mobile" type="text" name="mobile" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="client-email">
                    Email
                  </Label>
                  <AvField id="client-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="client-address">
                    Address
                  </Label>
                  <AvField id="client-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="streetAddressLabel" for="client-streetAddress">
                    Street Address
                  </Label>
                  <AvField id="client-streetAddress" type="text" name="streetAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="countyLabel" for="client-county">
                    County
                  </Label>
                  <AvField id="client-county" type="text" name="county" />
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="client-country">
                    Country
                  </Label>
                  <AvField id="client-country" type="text" name="country" />
                </AvGroup>
                <AvGroup>
                  <Label id="pinNumberLabel" for="client-pinNumber">
                    Pin Number
                  </Label>
                  <AvField id="client-pinNumber" type="string" className="form-control" name="pinNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="notesLabel" for="client-notes">
                    Notes
                  </Label>
                  <AvField id="client-notes" type="text" name="notes" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="client-status">
                    Status
                  </Label>
                  <AvInput
                    id="client-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && clientEntity.status) || 'ACTIVE'}
                  >
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="client-category">Category</Label>
                  <AvInput id="client-category" type="select" className="form-control" name="categoryId" required>
                    {clientCategories
                      ? clientCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  <Label for="client-title">Title</Label>
                  <AvInput id="client-title" type="select" className="form-control" name="titleId">
                    <option value="" key="0" />
                    {nameTitles
                      ? nameTitles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="client-idType">Id Type</Label>
                  <AvInput id="client-idType" type="select" className="form-control" name="idTypeId" required>
                    {idTypes
                      ? idTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/client" replace color="info">
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
  clientCategories: storeState.clientCategory.entities,
  nameTitles: storeState.nameTitle.entities,
  idTypes: storeState.idType.entities,
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess
});

const mapDispatchToProps = {
  getClientCategories,
  getNameTitles,
  getIdTypes,
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
)(ClientUpdate);
