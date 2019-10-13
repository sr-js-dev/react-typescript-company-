import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientCategory, defaultValue } from 'app/shared/model/client-category.model';

export const ACTION_TYPES = {
  FETCH_CLIENTCATEGORY_LIST: 'clientCategory/FETCH_CLIENTCATEGORY_LIST',
  FETCH_CLIENTCATEGORY: 'clientCategory/FETCH_CLIENTCATEGORY',
  CREATE_CLIENTCATEGORY: 'clientCategory/CREATE_CLIENTCATEGORY',
  UPDATE_CLIENTCATEGORY: 'clientCategory/UPDATE_CLIENTCATEGORY',
  DELETE_CLIENTCATEGORY: 'clientCategory/DELETE_CLIENTCATEGORY',
  RESET: 'clientCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientCategoryState = initialState, action): ClientCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTCATEGORY):
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

const apiUrl = 'api/client-categories';

// Actions

export const getEntities: ICrudGetAllAction<IClientCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTCATEGORY_LIST,
    payload: axios.get<IClientCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTCATEGORY,
    payload: axios.get<IClientCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTCATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
