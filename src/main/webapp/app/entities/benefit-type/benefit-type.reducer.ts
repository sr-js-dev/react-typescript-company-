import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBenefitType, defaultValue } from 'app/shared/model/benefit-type.model';

export const ACTION_TYPES = {
  FETCH_BENEFITTYPE_LIST: 'benefitType/FETCH_BENEFITTYPE_LIST',
  FETCH_BENEFITTYPE: 'benefitType/FETCH_BENEFITTYPE',
  CREATE_BENEFITTYPE: 'benefitType/CREATE_BENEFITTYPE',
  UPDATE_BENEFITTYPE: 'benefitType/UPDATE_BENEFITTYPE',
  DELETE_BENEFITTYPE: 'benefitType/DELETE_BENEFITTYPE',
  RESET: 'benefitType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBenefitType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BenefitTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: BenefitTypeState = initialState, action): BenefitTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BENEFITTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BENEFITTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BENEFITTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_BENEFITTYPE):
    case REQUEST(ACTION_TYPES.DELETE_BENEFITTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BENEFITTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BENEFITTYPE):
    case FAILURE(ACTION_TYPES.CREATE_BENEFITTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_BENEFITTYPE):
    case FAILURE(ACTION_TYPES.DELETE_BENEFITTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BENEFITTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BENEFITTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BENEFITTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_BENEFITTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BENEFITTYPE):
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

const apiUrl = 'api/benefit-types';

// Actions

export const getEntities: ICrudGetAllAction<IBenefitType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BENEFITTYPE_LIST,
    payload: axios.get<IBenefitType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBenefitType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BENEFITTYPE,
    payload: axios.get<IBenefitType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBenefitType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BENEFITTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBenefitType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BENEFITTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBenefitType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BENEFITTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
