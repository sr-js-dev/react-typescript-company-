import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientPolicy, defaultValue } from 'app/shared/model/client-policy.model';

export const ACTION_TYPES = {
  FETCH_CLIENTPOLICY_LIST: 'clientPolicy/FETCH_CLIENTPOLICY_LIST',
  FETCH_CLIENTPOLICY: 'clientPolicy/FETCH_CLIENTPOLICY',
  CREATE_CLIENTPOLICY: 'clientPolicy/CREATE_CLIENTPOLICY',
  UPDATE_CLIENTPOLICY: 'clientPolicy/UPDATE_CLIENTPOLICY',
  DELETE_CLIENTPOLICY: 'clientPolicy/DELETE_CLIENTPOLICY',
  RESET: 'clientPolicy/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientPolicy>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientPolicyState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientPolicyState = initialState, action): ClientPolicyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTPOLICY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTPOLICY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTPOLICY):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTPOLICY):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTPOLICY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTPOLICY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTPOLICY):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTPOLICY):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTPOLICY):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTPOLICY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTPOLICY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTPOLICY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTPOLICY):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTPOLICY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTPOLICY):
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

const apiUrl = 'api/client-policies';

// Actions

export const getEntities: ICrudGetAllAction<IClientPolicy> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTPOLICY_LIST,
    payload: axios.get<IClientPolicy>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientPolicy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTPOLICY,
    payload: axios.get<IClientPolicy>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientPolicy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTPOLICY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientPolicy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTPOLICY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientPolicy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTPOLICY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
