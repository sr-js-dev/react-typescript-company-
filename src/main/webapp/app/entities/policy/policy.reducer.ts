import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPolicy, defaultValue } from 'app/shared/model/policy.model';

export const ACTION_TYPES = {
  FETCH_POLICY_LIST: 'policy/FETCH_POLICY_LIST',
  FETCH_POLICY: 'policy/FETCH_POLICY',
  CREATE_POLICY: 'policy/CREATE_POLICY',
  UPDATE_POLICY: 'policy/UPDATE_POLICY',
  DELETE_POLICY: 'policy/DELETE_POLICY',
  RESET: 'policy/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPolicy>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PolicyState = Readonly<typeof initialState>;

// Reducer

export default (state: PolicyState = initialState, action): PolicyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POLICY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POLICY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_POLICY):
    case REQUEST(ACTION_TYPES.UPDATE_POLICY):
    case REQUEST(ACTION_TYPES.DELETE_POLICY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_POLICY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POLICY):
    case FAILURE(ACTION_TYPES.CREATE_POLICY):
    case FAILURE(ACTION_TYPES.UPDATE_POLICY):
    case FAILURE(ACTION_TYPES.DELETE_POLICY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_POLICY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_POLICY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_POLICY):
    case SUCCESS(ACTION_TYPES.UPDATE_POLICY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_POLICY):
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

const apiUrl = 'api/policies';

// Actions

export const getEntities: ICrudGetAllAction<IPolicy> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POLICY_LIST,
    payload: axios.get<IPolicy>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPolicy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POLICY,
    payload: axios.get<IPolicy>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPolicy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POLICY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPolicy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POLICY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPolicy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POLICY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
