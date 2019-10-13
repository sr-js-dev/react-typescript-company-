import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INameTitle, defaultValue } from 'app/shared/model/name-title.model';

export const ACTION_TYPES = {
  FETCH_NAMETITLE_LIST: 'nameTitle/FETCH_NAMETITLE_LIST',
  FETCH_NAMETITLE: 'nameTitle/FETCH_NAMETITLE',
  CREATE_NAMETITLE: 'nameTitle/CREATE_NAMETITLE',
  UPDATE_NAMETITLE: 'nameTitle/UPDATE_NAMETITLE',
  DELETE_NAMETITLE: 'nameTitle/DELETE_NAMETITLE',
  RESET: 'nameTitle/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INameTitle>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NameTitleState = Readonly<typeof initialState>;

// Reducer

export default (state: NameTitleState = initialState, action): NameTitleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NAMETITLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NAMETITLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NAMETITLE):
    case REQUEST(ACTION_TYPES.UPDATE_NAMETITLE):
    case REQUEST(ACTION_TYPES.DELETE_NAMETITLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NAMETITLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NAMETITLE):
    case FAILURE(ACTION_TYPES.CREATE_NAMETITLE):
    case FAILURE(ACTION_TYPES.UPDATE_NAMETITLE):
    case FAILURE(ACTION_TYPES.DELETE_NAMETITLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NAMETITLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_NAMETITLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NAMETITLE):
    case SUCCESS(ACTION_TYPES.UPDATE_NAMETITLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NAMETITLE):
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

const apiUrl = 'api/name-titles';

// Actions

export const getEntities: ICrudGetAllAction<INameTitle> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NAMETITLE_LIST,
    payload: axios.get<INameTitle>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INameTitle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NAMETITLE,
    payload: axios.get<INameTitle>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INameTitle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NAMETITLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INameTitle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NAMETITLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INameTitle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NAMETITLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
