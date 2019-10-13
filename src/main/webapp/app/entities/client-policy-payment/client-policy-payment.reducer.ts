import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientPolicyPayment, defaultValue } from 'app/shared/model/client-policy-payment.model';

export const ACTION_TYPES = {
  FETCH_CLIENTPOLICYPAYMENT_LIST: 'clientPolicyPayment/FETCH_CLIENTPOLICYPAYMENT_LIST',
  FETCH_CLIENTPOLICYPAYMENT: 'clientPolicyPayment/FETCH_CLIENTPOLICYPAYMENT',
  CREATE_CLIENTPOLICYPAYMENT: 'clientPolicyPayment/CREATE_CLIENTPOLICYPAYMENT',
  UPDATE_CLIENTPOLICYPAYMENT: 'clientPolicyPayment/UPDATE_CLIENTPOLICYPAYMENT',
  DELETE_CLIENTPOLICYPAYMENT: 'clientPolicyPayment/DELETE_CLIENTPOLICYPAYMENT',
  RESET: 'clientPolicyPayment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientPolicyPayment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientPolicyPaymentState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientPolicyPaymentState = initialState, action): ClientPolicyPaymentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTPOLICYPAYMENT):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTPOLICYPAYMENT):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTPOLICYPAYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTPOLICYPAYMENT):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTPOLICYPAYMENT):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTPOLICYPAYMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTPOLICYPAYMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTPOLICYPAYMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTPOLICYPAYMENT):
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

const apiUrl = 'api/client-policy-payments';

// Actions

export const getEntities: ICrudGetAllAction<IClientPolicyPayment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT_LIST,
    payload: axios.get<IClientPolicyPayment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientPolicyPayment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTPOLICYPAYMENT,
    payload: axios.get<IClientPolicyPayment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientPolicyPayment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTPOLICYPAYMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientPolicyPayment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTPOLICYPAYMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientPolicyPayment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTPOLICYPAYMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
