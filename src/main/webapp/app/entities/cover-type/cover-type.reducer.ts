import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoverType, defaultValue } from 'app/shared/model/cover-type.model';

export const ACTION_TYPES = {
  FETCH_COVERTYPE_LIST: 'coverType/FETCH_COVERTYPE_LIST',
  FETCH_COVERTYPE: 'coverType/FETCH_COVERTYPE',
  CREATE_COVERTYPE: 'coverType/CREATE_COVERTYPE',
  UPDATE_COVERTYPE: 'coverType/UPDATE_COVERTYPE',
  DELETE_COVERTYPE: 'coverType/DELETE_COVERTYPE',
  RESET: 'coverType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoverType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CoverTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: CoverTypeState = initialState, action): CoverTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVERTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVERTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COVERTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_COVERTYPE):
    case REQUEST(ACTION_TYPES.DELETE_COVERTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COVERTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVERTYPE):
    case FAILURE(ACTION_TYPES.CREATE_COVERTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_COVERTYPE):
    case FAILURE(ACTION_TYPES.DELETE_COVERTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVERTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_COVERTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVERTYPE):
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

const apiUrl = 'api/cover-types';

// Actions

export const getEntities: ICrudGetAllAction<ICoverType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COVERTYPE_LIST,
    payload: axios.get<ICoverType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICoverType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVERTYPE,
    payload: axios.get<ICoverType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICoverType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVERTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoverType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVERTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoverType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVERTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
