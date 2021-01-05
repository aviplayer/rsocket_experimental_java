import {createReducer, PayloadAction} from '@reduxjs/toolkit';
import {AppState, ContentType, ReceiveMessage} from './app.types';
import {appStarted, rsocketAction,} from './app.actions';

export const initialState = {
  path: '/',
  contentType: ContentType.NotStarted,
  messages: []
};

const setMessage = (m: string, ...messages: string[]): string[] => {
  messages.push(m);
  return messages;
}

export const app = createReducer<AppState>(initialState, {
  [appStarted.type]: (state) => ({
    ...state,
    contentType: ContentType.Empty,
    path: "/test"
  }),
  [rsocketAction.type]: (
    state,
    {payload}: PayloadAction<ReceiveMessage>) => ({
    ...state,
    messages: setMessage(payload.message, ...state.messages)
  })
});
