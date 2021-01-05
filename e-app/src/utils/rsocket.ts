import {
  BufferEncoders,
  encodeAndAddWellKnownMetadata,
  MAX_STREAM_ID,
  MESSAGE_RSOCKET_COMPOSITE_METADATA,
  MESSAGE_RSOCKET_ROUTING,
  RSocketClient
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import ReactiveSocketTypes from 'rsocket-types';
import {eventChannel} from '@redux-saga/core';


const encodeRoute = (route: any) => {
  const length = Buffer.byteLength(route, 'utf8');
  const buffer = Buffer.alloc(1);
  //@ts-ignore
  buffer.writeInt8(length);
  return Buffer.concat([buffer, Buffer.from(route, 'utf8')]);
}

const getClient = (url: string) => {
  return new Promise<ReactiveSocketTypes.ReactiveSocket<any, any>>(
    (resolve, reject): void => {
      try {
        const client = new RSocketClient({
          setup: {
            keepAlive: 5000,
            lifetime: 30000,
            dataMimeType: 'application/octet-stream',
            metadataMimeType: MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
          },
          transport: new RSocketWebSocketClient({
            // eslint-disable-next-line no-restricted-globals
            url: `ws://${url}`
          }, BufferEncoders),
        });
        client.connect().then(
          (socket) => {
            resolve(socket)
          },
          (err) => {
            reject(err)
          },
        )

      } catch (e) {
        reject(e);
      }
    });
}

// @ts-ignore
export const createChannel = (urlF: string) => {
  const url = urlF;
  let socket: ReactiveSocketTypes.ReactiveSocket<any, any>;
  return {
    // @ts-ignore
    rSocketChannel: eventChannel((emitter) => {
      let rsSub: { unsubscribe: () => void; } | null = null;
      const routeMetadata = encodeRoute('stringSplit');
      const metadata = encodeAndAddWellKnownMetadata(
        Buffer.alloc(0),
        MESSAGE_RSOCKET_ROUTING,
        routeMetadata
      );
      const subscribe = async () => {
        socket = await getClient.call(null, url);
        socket.requestStream({
          data: Buffer.from("Hello from client!!!", 'utf8'),
          metadata,
        }).subscribe({
          onNext: (payload: any) => {
            emitter(payload.data.toString('utf8'))
          },
          onError: (error: any) => {
            console.error(error);
            emitter(error)
          },
          onSubscribe: (_subscription) => _subscription.request(MAX_STREAM_ID),
        });
      }
      subscribe()
        .then(
          (m) => console.log(m),
          (e) => console.error(e)
        );
      return () => socket?.close();
    }),
  }
}

