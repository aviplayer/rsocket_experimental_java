import {Action} from "redux";

export interface AppState {
  path: string;
  contentType: ContentType;
  messages: string[];
}

export enum ContentType {
  Empty,
  NotStarted,
}

export interface ReceiveMessage {
  message: string
}

