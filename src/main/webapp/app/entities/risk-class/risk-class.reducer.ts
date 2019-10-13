import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRiskClass, defaultValue } from 'app/shared/model/risk-class.model';

export const ACTION_TYPES = {
  FETCH_RISKCLASS_LIST: 'riskClass/FETCH_RISKCLASS_LIST',
  FETCH_RISKCLASS: 'riskClass/FETCH_RISKCLASS',
  CREATE_RISKCLASS: 'riskClass/CREATE_RISKCLASS',
  UPDATE_RISKCLASS: 'riskClass/UPDATE_RISKCLASS',
  DELETE_RISKCLASS: 'riskClass/DELETE_RISKCLASS',
  RESET: 'riskClass/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRiskClass>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RiskClassState = Readonly<typeof initialState>;

// Reducer

export default (state: RiskClassState = initialState, action): RiskClassState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RISKCLASS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RISKCLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RISKCLASS):
    case REQUEST(ACTION_TYPES.UPDATE_RISKCLASS):
    case REQUEST(ACTION_TYPES.DELETE_RISKCLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RISKCLASS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RISKCLASS):
    case FAILURE(ACTION_TYPES.CREATE_RISKCLASS):
    case FAILURE(ACTION_TYPES.UPDATE_RISKCLASS):
    case FAILURE(ACTION_TYPES.DELETE_RISKCLASS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKCLASS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKCLASS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RISKCLASS):
    case SUCCESS(ACTION_TYPES.UPDATE_RISKCLASS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RISKCLASS):
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

const apiUrl = 'api/risk-classes';

// Actions

export const getEntities: ICrudGetAllAction<IRiskClass> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RISKCLASS_LIST,
    payload: axios.get<IRiskClass>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRiskClass> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RISKCLASS,
    payload: axios.get<IRiskClass>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRiskClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RISKCLASS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRiskClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RISKCLASS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRiskClass> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RISKCLASS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
