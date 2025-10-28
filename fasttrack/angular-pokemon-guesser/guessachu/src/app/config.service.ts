// config.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';
import type { Difficulty } from './leaderboard.service';

const KEY = 'app.config.v1'; // bump version if shape changes

type ConfigState = {
  difficulty: Difficulty;
  theme: string;
};

const DEFAULTS: ConfigState = {
  difficulty: 'Beginner Trainer',
  theme: 'Light',
};

const DIFFICULTIES: Difficulty[] = [
  'Beginner Trainer',
  'Experienced Trainer',
  'Pokemon Master',
  'Champion Mode',
];
function coerceDifficulty(v: any): Difficulty {
  return DIFFICULTIES.includes(v) ? (v as Difficulty) : DEFAULTS.difficulty;
}

@Injectable({ providedIn: 'root' })
export class ConfigService {
  private state$ = new BehaviorSubject<ConfigState>(this.load() ?? DEFAULTS);

  // expose fields
  difficulty = this.state$.asObservable().pipe(map(s => s.difficulty));
  theme      = this.state$.asObservable().pipe(map(s => s.theme));

  // snapshots
  get difficultyValue() { return this.state$.value.difficulty; }
  get themeValue()      { return this.state$.value.theme; }

  updateDifficulty(d: Difficulty) {
    this.patch({ difficulty: d });
  }
  updateTheme(t: string) {
    this.patch({ theme: t });
  }

  // ---- helpers ----
  private patch(partial: Partial<ConfigState>) {
    const next = { ...this.state$.value, ...partial };
    this.state$.next(next);
    this.save(next);
  }

  private load(): ConfigState | null {
    try {
      const raw = localStorage.getItem(KEY);
      if (!raw) return null;
      const parsed = JSON.parse(raw);
      return {
        difficulty: coerceDifficulty(parsed?.difficulty),
        theme: typeof parsed?.theme === 'string' ? parsed.theme : DEFAULTS.theme,
      };
    } catch {
      return null;
    }
  }

  private save(state: ConfigState) {
    try {
      localStorage.setItem(KEY, JSON.stringify(state));
    } catch {}
  }

  constructor() {
    // cross-tab sync
    window.addEventListener('storage', (e) => {
      if (e.key === KEY && e.newValue) {
        try {
          const parsed = JSON.parse(e.newValue);
          this.state$.next({
            difficulty: coerceDifficulty(parsed?.difficulty),
            theme: typeof parsed?.theme === 'string' ? parsed.theme : DEFAULTS.theme,
          });
        } catch {}
      }
    });
  }
}