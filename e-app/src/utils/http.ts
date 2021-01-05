import axios from 'axios';
import applyCaseMiddleware from 'axios-case-converter';

export const httpClient = applyCaseMiddleware(axios.create());

export const ausHttpClient = applyCaseMiddleware(
  axios.create({ baseURL: '/bizapps/aus-analyzer' })
);
