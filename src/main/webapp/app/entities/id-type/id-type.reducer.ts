import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIdType, defaultValue } from 'app/shared/model/id-type.model';

export const ACTION_TYPES = {
  FETCH_IDTYPE_LIST: 'idType/FETCH_IDTYPE_LIST',
  FETCH_IDTYPE: 'idType/FETCH_IDTYPE',
  CREATE_IDTYPE: 'idType/CREATE_IDTYPE',
  UPDATE_IDTYPE: 'idType/UPDATE_IDTYPE',
  DELETE_IDTYPE: 'idType/DELETE_IDTYPE',
  RESET: 'idType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIdType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type IdTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: IdTypeState = initialState, action): IdTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IDTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IDTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_IDTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_IDTYPE):
    case REQUEST(ACTION_TYPES.DELETE_IDTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_IDTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IDTYPE):
    case FAILURE(ACTION_TYPES.CREATE_IDTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_IDTYPE):
    case FAILURE(ACTION_TYPES.DELETE_IDTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_IDTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_IDTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_IDTYPE):
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

const apiUrl = 'api/id-types';

// Actions

export const getEntities: ICrudGetAllAction<IIdType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_IDTYPE_LIST,
    payload: axios.get<IIdType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IIdType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IDTYPE,
    payload: axios.get<IIdType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIdType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IDTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIdType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IDTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIdType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IDTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
