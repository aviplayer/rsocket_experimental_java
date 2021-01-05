import {call, put, select, takeEvery, takeLatest,} from '@redux-saga/core/effects';
import {appStart, appStarted, rsocketAction,} from './app.actions';

import {getPath} from './app.selectors';
import {history} from '../history';
import {createChannel} from '../../utils'

const client = createChannel("localhost:7100");

function* appStartSaga() {
  try {
    yield put(appStarted());
  } catch (e) {
    console.error("Application failed")
  }
}

function* messageReceivedSaga(message: string) {
  try {
    yield put(
      rsocketAction({
        message: message
      })
    );
  } catch(e){
    console.error(e);
  }
}


function* navigatedByActionSaga() {
  const path = yield select(getPath);
  if (history.location.pathname !== path) {
    history.push(path);
  }
}

export function* app(): Generator {
  yield takeLatest(appStart.type, appStartSaga);
  yield takeLatest(appStarted.type, navigatedByActionSaga);
  yield takeEvery(client.rSocketChannel, messageReceivedSaga);
}
