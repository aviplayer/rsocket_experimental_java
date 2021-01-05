import { AxiosResponse } from 'axios';
import { httpClient } from '../../utils';

export const getSession = (): Promise<AxiosResponse<AxiosResponse>> =>
  httpClient.get('api/session');
