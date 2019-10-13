import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBenefit, defaultValue } from 'app/shared/model/benefit.model';

export const ACTION_TYPES = {
  FETCH_BENEFIT_LIST: 'benefit/FETCH_BENEFIT_LIST',
  FETCH_BENEFIT: 'benefit/FETCH_BENEFIT',
  CREATE_BENEFIT: 'benefit/CREATE_BENEFIT',
  UPDATE_BENEFIT: 'benefit/UPDATE_BENEFIT',
  DELETE_BENEFIT: 'benefit/DELETE_BENEFIT',
  RESET: 'benefit/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBenefit>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BenefitState = Readonly<typeof initialState>;

// Reducer

export default (state: BenefitState = initialState, action): BenefitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BENEFIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BENEFIT):
    case REQUEST(ACTION_TYPES.UPDATE_BENEFIT):
    case REQUEST(ACTION_TYPES.DELETE_BENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BENEFIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BENEFIT):
    case FAILURE(ACTION_TYPES.CREATE_BENEFIT):
    case FAILURE(ACTION_TYPES.UPDATE_BENEFIT):
    case FAILURE(ACTION_TYPES.DELETE_BENEFIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BENEFIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BENEFIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BENEFIT):
    case SUCCESS(ACTION_TYPES.UPDATE_BENEFIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BENEFIT):
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

const apiUrl = 'api/benefits';

// Actions

export const getEntities: ICrudGetAllAction<IBenefit> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BENEFIT_LIST,
    payload: axios.get<IBenefit>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBenefit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BENEFIT,
    payload: axios.get<IBenefit>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BENEFIT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BENEFIT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBenefit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BENEFIT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
