export interface IDicesRun {
  id?: string;
  gameId?: string;
  playerId?: string;
  firstDice?: number | null;
  middleDice?: number | null;
  lastDice?: number | null;
}

export const defaultValue: Readonly<IDicesRun> = {};
