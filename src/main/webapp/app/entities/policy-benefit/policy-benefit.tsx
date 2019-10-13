import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './policy-benefit.reducer';
import { IPolicyBenefit } from 'app/shared/model/policy-benefit.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPolicyBenefitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IPolicyBenefitState = IPaginationBaseState;

export class PolicyBenefit extends React.Component<IPolicyBenefitProps, IPolicyBenefitState> {
  state: IPolicyBenefitState = {
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
    const { policyBenefitList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="policy-benefit-heading">
          Policy Benefits
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Policy Benefit
          </Link>
        </h2>
        <div className="table-responsive">
          {policyBenefitList && policyBenefitList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('benefitRate')}>
                    Benefit Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('benefitValue')}>
                    Benefit Value <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('benefitMinValue')}>
                    Benefit Min Value <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Benefit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Policy <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {policyBenefitList.map((policyBenefit, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${policyBenefit.id}`} color="link" size="sm">
                        {policyBenefit.id}
                      </Button>
                    </td>
                    <td>{policyBenefit.benefitRate}</td>
                    <td>{policyBenefit.benefitValue}</td>
                    <td>{policyBenefit.benefitMinValue}</td>
                    <td>
                      {policyBenefit.benefitId ? <Link to={`benefit/${policyBenefit.benefitId}`}>{policyBenefit.benefitId}</Link> : ''}
                    </td>
                    <td>{policyBenefit.policyId ? <Link to={`policy/${policyBenefit.policyId}`}>{policyBenefit.policyId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${policyBenefit.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${policyBenefit.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${policyBenefit.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Policy Benefits found</div>
          )}
        </div>
        <div className={policyBenefitList && policyBenefitList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ policyBenefit }: IRootState) => ({
  policyBenefitList: policyBenefit.entities,
  totalItems: policyBenefit.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PolicyBenefit);
