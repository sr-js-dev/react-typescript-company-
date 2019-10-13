import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, Input, Form, FormGroup } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

import PhoneInput from 'react-phone-number-input';
import ReactFlagsSelect from 'react-flags-select';
import 'react-phone-number-input/style.css';
import 'react-flags-select/css/react-flags-select.css';
import Logoimage from './logoimage';
export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICompanyUpdateState {
  isNew: boolean;
  telephone: string;
  phonenum: string;
}

export class CompanyUpdate extends React.Component<ICompanyUpdateProps, ICompanyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id, telephone: '', phonenum: ''
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
      const { companyEntity } = this.props;
      const entity = {
        ...companyEntity,
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
    this.props.history.push('/entity/company');
  };
  render() {
    const { companyEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ibsApp.company.home.createOrEditLabel">Create or edit a Company</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <Form onSubmit = { this.saveEntity }>
                    {!isNew ? (
                        <FormGroup>
                          <Label for="company-id">ID</Label>
                          <Input id="company-id" type="text" className="form-control" name="id" required readOnly />
                        </FormGroup>
                      ) : null}
                      <FormGroup>
                        <Row>
                            <Label sm="2">User Type</Label>
                            <Col>
                                  <Input
                                    id="company-usertype"
                                    type="select"
                                    name="usertype"
                                    value={(!isNew && companyEntity.usertype) || 'COMPANY'}
                                  >
                                    <option value="COMPANY">COMPANY</option>
                                    <option value="INDIVIDUAL">INDIVIDUAL</option>
                                  </Input>
                            </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">First Name</Label>
                          <Col sm="10">
                            <Input id="company-firstName" type="text" required name="firstName" defaultValue="" placeholder="Enter first name" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Middle Name</Label>
                          <Col sm="10">
                            <Input id="company-middleName" type="text" required name="middleName" defaultValue="" placeholder="Enter middle name" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Last Name</Label>
                          <Col sm="10">
                            <Input id="company-lastName" type="text" required name="lastName" defaultValue="" placeholder="Enter last name" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Name</Label>
                          <Col sm="10">
                            <Input id="company-name" type="text" required name="name" defaultValue="" placeholder="Enter name" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Email</Label>
                          <Col sm="10">
                            <Input id="company-email" type="email" required name="email" defaultValue="" placeholder="Enter email address" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Display Name</Label>
                          <Col sm="10">
                            <Input  id="company-displayName" type="text" required name="displayName" defaultValue="" placeholder="Enter display name" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Logo</Label>
                          <Col sm="10">
                            <Logoimage id="company-logo" name="logo" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Telephone</Label>
                          <Col sm="10">
                            <PhoneInput
                                placeholder = "Enter phone number"
                                value = { this.state.telephone }
                                id="company-telephone"
                                required
                                name = "telephone"
                                onChange = {telephone => this.setState({ telephone })}/>
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Contract Person</Label>
                          <Col sm="10">
                            <Input company-contactPersion name="contactPersion" required type="text" defaultValue="" placeholder="Enter contract person" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Phone number</Label>
                          <Col sm="10">
                            <PhoneInput
                                placeholder="Enter phone number"
                                value={ this.state.phonenum }
                                id="company-mobile"
                                required
                                name="mobile"
                                onChange={ phonenum => this.setState({ phonenum }) } />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Address</Label>
                          <Col sm="10">
                            <Input id="company-address" name="address" required type="text" defaultValue="" placeholder="Enter address" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Street Address</Label>
                          <Col sm="10">
                            <Input id="company-streetAddress" name="streetAddress" required type="text" defaultValue="" placeholder="Enter street address" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">County</Label>
                          <Col sm="10">
                            <Input id="company-county" name="county" required type="text" defaultValue="" placeholder="Enter county" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">Country</Label>
                          <Col sm="10">
                            <ReactFlagsSelect id="company-country" required name="country"/>
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Label sm="2">PIN Number</Label>
                          <Col sm="10">
                            <Input id="company-pinNumber" name="pinNumber" required type="number" placeholder="Enter PIN number" />
                          </Col>
                        </Row>
                      </FormGroup>
                      <FormGroup>
                        <Row>
                          <Col sm="12" className="text-center">
                                 <Button tag={Link} id="cancel-save" to="/entity/company" replace color="info">
                                  <FontAwesomeIcon icon="arrow-left" />
                                    &nbsp;
                                    <span className="d-none d-md-inline">Back</span>
                                  </Button>
                                  &nbsp;
                                  <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                                    <FontAwesomeIcon icon="save" />
                                    &nbsp; Save
                                  </Button>
                          </Col>
                        </Row>
                      </FormGroup>
                    </Form>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating,
  updateSuccess: storeState.company.updateSuccess
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
)(CompanyUpdate);
