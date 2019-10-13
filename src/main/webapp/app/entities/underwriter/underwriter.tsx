import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './underwriter.reducer';
import { IUnderwriter } from 'app/shared/model/underwriter.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IUnderwriterProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IUnderwriterState = IPaginationBaseState;

export class Underwriter extends React.Component<IUnderwriterProps, IUnderwriterState> {
  state: IUnderwriterState = {
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
    const { underwriterList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="underwriter-heading">
          Underwriters
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Underwriter
          </Link>
        </h2>
        <div className="table-responsive">
          {underwriterList && underwriterList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('description')}>
                    Description <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('logo')}>
                    Logo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('binder')}>
                    Binder <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('website')}>
                    Website <FontAwesomeIcon icon="sort" />
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
                  <th className="hand" onClick={this.sort('status')}>
                    Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {underwriterList.map((underwriter, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${underwriter.id}`} color="link" size="sm">
                        {underwriter.id}
                      </Button>
                    </td>
                    <td>{underwriter.name}</td>
                    <td>{underwriter.description}</td>
                    <td>{underwriter.logo}</td>
                    <td>{underwriter.binder}</td>
                    <td>{underwriter.website}</td>
                    <td>{underwriter.contactPersion}</td>
                    <td>{underwriter.telephone}</td>
                    <td>{underwriter.mobile}</td>
                    <td>{underwriter.email}</td>
                    <td>{underwriter.address}</td>
                    <td>{underwriter.streetAddress}</td>
                    <td>{underwriter.county}</td>
                    <td>{underwriter.country}</td>
                    <td>{underwriter.pinNumber}</td>
                    <td>{underwriter.status}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${underwriter.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${underwriter.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${underwriter.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Underwriters found</div>
          )}
        </div>
        <div className={underwriterList && underwriterList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ underwriter }: IRootState) => ({
  underwriterList: underwriter.entities,
  totalItems: underwriter.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Underwriter);
