import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRiskCategory, defaultValue } from 'app/shared/model/risk-category.model';

export const ACTION_TYPES = {
  FETCH_RISKCATEGORY_LIST: 'riskCategory/FETCH_RISKCATEGORY_LIST',
  FETCH_RISKCATEGORY: 'riskCategory/FETCH_RISKCATEGORY',
  CREATE_RISKCATEGORY: 'riskCategory/CREATE_RISKCATEGORY',
  UPDATE_RISKCATEGORY: 'riskCategory/UPDATE_RISKCATEGORY',
  DELETE_RISKCATEGORY: 'riskCategory/DELETE_RISKCATEGORY',
  RESET: 'riskCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRiskCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RiskCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: RiskCategoryState = initialState, action): RiskCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RISKCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RISKCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RISKCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_RISKCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_RISKCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RISKCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RISKCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_RISKCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_RISKCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_RISKCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RISKCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_RISKCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RISKCATEGORY):
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

const apiUrl = 'api/risk-categories';

// Actions

export const getEntities: ICrudGetAllAction<IRiskCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RISKCATEGORY_LIST,
    payload: axios.get<IRiskCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRiskCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RISKCATEGORY,
    payload: axios.get<IRiskCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRiskCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RISKCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRiskCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RISKCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRiskCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RISKCATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
