import dayjs from 'dayjs';

export interface IGame {
  id?: string;
  winnerPlayerId?: string;
  date?: string;
}

export const defaultValue: Readonly<IGame> = {};
