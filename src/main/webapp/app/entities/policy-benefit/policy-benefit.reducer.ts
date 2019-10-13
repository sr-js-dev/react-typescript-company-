import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPolicyBenefit, defaultValue } from 'app/shared/model/policy-benefit.model';

export const ACTION_TYPES = {
  FETCH_POLICYBENEFIT_LIST: 'policyBenefit/FETCH_POLICYBENEFIT_LIST',
  FETCH_POLICYBENEFIT: 'policyBenefit/FETCH_POLICYBENEFIT',
  CREATE_POLICYBENEFIT: 'policyBenefit/CREATE_POLICYBENEFIT',
  UPDATE_POLICYBENEFIT: 'policyBenefit/UPDATE_POLICYBENEFIT',
  DELETE_POLICYBENEFIT: 'policyBenefit/DELETE_POLICYBENEFIT',
  RESET: 'policyBenefit/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPolicyBenefit>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PolicyBenefitState = Readonly<typeof initialState>;

// Reducer

export default (state: PolicyBenefitState = initialState, action): PolicyBenefitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POLICYBENEFIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POLICYBENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_POLICYBENEFIT):
    case REQUEST(ACTION_TYPES.UPDATE_POLICYBENEFIT):
    case REQUEST(ACTION_TYPES.DELETE_POLICYBENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_POLICYBENEFIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POLICYBENEFIT):
    case FAILURE(ACTION_TYPES.CREATE_POLICYBENEFIT):
    case FAILURE(ACTION_TYPES.UPDATE_POLICYBENEFIT):
    case FAILURE(ACTION_TYPES.DELETE_POLICYBENEFIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_POLICYBENEFIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_POLICYBENEFIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_POLICYBENEFIT):
    case SUCCESS(ACTION_TYPES.UPDATE_POLICYBENEFIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_POLICYBENEFIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/policy-benefits';

// Actions

export const getEntities: ICrudGetAllAction<IPolicyBenefit> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POLICYBENEFIT_LIST,
    payload: axios.get<IPolicyBenefit>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPolicyBenefit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POLICYBENEFIT,
    payload: axios.get<IPolicyBenefit>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPolicyBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POLICYBENEFIT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPolicyBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POLICYBENEFIT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPolicyBenefit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POLICYBENEFIT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
