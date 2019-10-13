import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IClientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IClientState = IPaginationBaseState;

export class Client extends React.Component<IClientProps, IClientState> {
  state: IClientState = {
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
    const { clientList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="client-heading">
          Clients
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Client
          </Link>
        </h2>
        <div className="table-responsive">
          {clientList && clientList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('firstName')}>
                    First Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('middleName')}>
                    Middle Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastName')}>
                    Last Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('clientName')}>
                    Client Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('ledgerName')}>
                    Ledger Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('clientPrintName')}>
                    Client Print Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('idNumber')}>
                    Id Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('contactPersion')}>
                    Contact Persion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('telephone')}>
                    Telephone <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('mobile')}>
                    Mobile <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('address')}>
                    Address <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('streetAddress')}>
                    Street Address <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('county')}>
                    County <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('country')}>
                    Country <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('pinNumber')}>
                    Pin Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('notes')}>
                    Notes <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('status')}>
                    Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Category <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Title <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Id Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {clientList.map((client, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${client.id}`} color="link" size="sm">
                        {client.id}
                      </Button>
                    </td>
                    <td>{client.firstName}</td>
                    <td>{client.middleName}</td>
                    <td>{client.lastName}</td>
                    <td>{client.clientName}</td>
                    <td>{client.ledgerName}</td>
                    <td>{client.clientPrintName}</td>
                    <td>{client.idNumber}</td>
                    <td>{client.contactPersion}</td>
                    <td>{client.telephone}</td>
                    <td>{client.mobile}</td>
                    <td>{client.email}</td>
                    <td>{client.address}</td>
                    <td>{client.streetAddress}</td>
                    <td>{client.county}</td>
                    <td>{client.country}</td>
                    <td>{client.pinNumber}</td>
                    <td>{client.notes}</td>
                    <td>{client.status}</td>
                    <td>{client.categoryId ? <Link to={`client-category/${client.categoryId}`}>{client.categoryId}</Link> : ''}</td>
                    <td>{client.titleId ? <Link to={`name-title/${client.titleId}`}>{client.titleId}</Link> : ''}</td>
                    <td>{client.idTypeId ? <Link to={`id-type/${client.idTypeId}`}>{client.idTypeId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${client.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${client.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${client.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Clients found</div>
          )}
        </div>
        <div className={clientList && clientList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ client }: IRootState) => ({
  clientList: client.entities,
  totalItems: client.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Client);
