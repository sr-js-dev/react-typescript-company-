import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './client-policy.reducer';
import { IClientPolicy } from 'app/shared/model/client-policy.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IClientPolicyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IClientPolicyState = IPaginationBaseState;

export class ClientPolicy extends React.Component<IClientPolicyProps, IClientPolicyState> {
  state: IClientPolicyState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { clientPolicyList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="client-policy-heading">
          Client Policies
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Client Policy
          </Link>
        </h2>
        <div className="table-responsive">
          {clientPolicyList && clientPolicyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('policyDate')}>
                    Policy Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('invoiceNo')}>
                    Invoice No <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('startDate')}>
                    Start Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('endDate')}>
                    End Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('primiumPayable')}>
                    Primium Payable <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('openPayable')}>
                    Open Payable <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paymentStatus')}>
                    Payment Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Client <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Policy <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {clientPolicyList.map((clientPolicy, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${clientPolicy.id}`} color="link" size="sm">
                        {clientPolicy.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={clientPolicy.policyDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{clientPolicy.invoiceNo}</td>
                    <td>
                      <TextFormat type="date" value={clientPolicy.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={clientPolicy.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{clientPolicy.primiumPayable}</td>
                    <td>{clientPolicy.openPayable}</td>
                    <td>{clientPolicy.paymentStatus}</td>
                    <td>{clientPolicy.clientId ? <Link to={`client/${clientPolicy.clientId}`}>{clientPolicy.clientId}</Link> : ''}</td>
                    <td>{clientPolicy.policyId ? <Link to={`policy/${clientPolicy.policyId}`}>{clientPolicy.policyId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${clientPolicy.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${clientPolicy.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${clientPolicy.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Client Policies found</div>
          )}
        </div>
        <div className={clientPolicyList && clientPolicyList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ clientPolicy }: IRootState) => ({
  clientPolicyList: clientPolicy.entities,
  totalItems: clientPolicy.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientPolicy);
