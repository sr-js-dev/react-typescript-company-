import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ICompanyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ICompanyState = IPaginationBaseState;

export class Company extends React.Component<ICompanyProps, ICompanyState> {
  state: ICompanyState = {
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
    const { companyList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="company-heading">
          Companies
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Company
          </Link>
        </h2>
        <div className="table-responsive">
          {companyList && companyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usertype')}>
                    Usertype <FontAwesomeIcon icon="sort" />
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
                  <th className="hand" onClick={this.sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('displayName')}>
                    Display Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('logo')}>
                    Logo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('telephone')}>
                    Telephone <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('contactPersion')}>
                    Contact Persion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('mobile')}>
                    Mobile <FontAwesomeIcon icon="sort" />
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
                  <th />
                </tr>
              </thead>
              <tbody>
                {companyList.map((company, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${company.id}`} color="link" size="sm">
                        {company.id}
                      </Button>
                    </td>
                    <td>{company.usertype}</td>
                    <td>{company.firstName}</td>
                    <td>{company.middleName}</td>
                    <td>{company.lastName}</td>
                    <td>{company.name}</td>
                    <td>{company.email}</td>
                    <td>{company.displayName}</td>
                    <td>{company.logo}</td>
                    <td>{company.telephone}</td>
                    <td>{company.contactPersion}</td>
                    <td>{company.mobile}</td>
                    <td>{company.address}</td>
                    <td>{company.streetAddress}</td>
                    <td>{company.county}</td>
                    <td>{company.country}</td>
                    <td>{company.pinNumber}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${company.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${company.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${company.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Companies found</div>
          )}
        </div>
        <div className={companyList && companyList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ company }: IRootState) => ({
  companyList: company.entities,
  totalItems: company.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Company);
