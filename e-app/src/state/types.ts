import { AppState } from './app/app.types';

export interface RootState {
  app: AppState;
}

export interface NavigationMeta {
  isStartup: boolean;
}

