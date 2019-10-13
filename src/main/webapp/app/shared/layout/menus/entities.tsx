import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/company">
      Company
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/benefit-type">
      Benefit Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/benefit">
      Benefit
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/underwriter">
      Underwriter
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/client-category">
      Client Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/name-title">
      Name Title
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/id-type">
      Id Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/client">
      Client
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/product-type">
      Product Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/risk-category">
      Risk Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/risk-class">
      Risk Class
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/cover-type">
      Cover Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/policy">
      Policy
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/policy-benefit">
      Policy Benefit
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/client-policy">
      Client Policy
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/client-policy-payment">
      Client Policy Payment
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
