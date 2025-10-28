import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';

export type Difficulty =
    | 'Beginner Trainer'
    | 'Experienced Trainer'
    | 'Pokemon Master'
    | 'Champion Mode';

export interface LeaderEntry {
    name: string;
    score: number;
    at?: string; // ISO timestamp
}

type Leaderboards = Record<Difficulty, LeaderEntry[]>;

const STORAGE_KEY = 'app.leaderboards.v1';

// start with 4 empty boards
const DEFAULT_LEADERBOARDS: Leaderboards = {
    'Beginner Trainer': [],
    'Experienced Trainer': [],
    'Pokemon Master': [],
    'Champion Mode': [],
};

@Injectable({ providedIn: 'root' })
export class LeaderboardService {
    private state$ = new BehaviorSubject<Leaderboards>(this.load() ?? DEFAULT_LEADERBOARDS);

    /** Observable of a board for a given difficulty (top 10 only) */
    board$(difficulty: Difficulty) {
        return this.state$.pipe(map(s => s[difficulty]));
    }

    /** Snapshot getter (no subscribe) */
    getBoard(difficulty: Difficulty): LeaderEntry[] {
        return this.state$.value[difficulty];
    }

    /** Returns exactly 10 rows padded with placeholders (useful for UI) */
    boardPadded$(difficulty: Difficulty) {
        return this.board$(difficulty).pipe(
            map(rows => this.padTo10(rows))
        );
    }

    /** True if this score would land in the Top 10 (does NOT mutate state). */
    wouldQualify(difficulty: Difficulty, score: number): boolean {
        const board = this.state$.value[difficulty] ?? [];
        if (board.length < 10) return true;
        const sorted = [...board].sort(
            (a, b) => b.score - a.score || (a.at ?? '').localeCompare(b.at ?? '')
        );
        const cutoff = sorted[9].score;
        return score > cutoff;
    }


    /** Submit a score. Keeps only top 10. Returns { accepted, rank }. */
    submit(difficulty: Difficulty, name: string, score: number): { accepted: boolean; rank: number } {
        const now = new Date().toISOString();
        const current = this.clone(this.state$.value);
        const list = [...current[difficulty], { name, score, at: now }];

        // sort by score desc; tie-breaker: earlier timestamp first
        list.sort((a, b) => b.score - a.score || (a.at ?? '').localeCompare(b.at ?? ''));

        // rank of the new record before slicing
        const rank = list.findIndex(e => e.name === name && e.score === score && e.at === now) + 1;

        // keep only top 10
        current[difficulty] = list.slice(0, 10);

        this.state$.next(current);
        this.save(current);

        return { accepted: rank > 0 && rank <= 10, rank };
    }

    /** Clear one board */
    resetBoard(difficulty: Difficulty) {
        const next = this.clone(this.state$.value);
        next[difficulty] = [];
        this.state$.next(next);
        this.save(next);
    }

    /** Clear all boards */
    resetAll() {
        this.state$.next(this.clone(DEFAULT_LEADERBOARDS));
        this.save(this.state$.value);
    }

    // ---------- helpers / persistence ----------

    private padTo10(rows: LeaderEntry[]): LeaderEntry[] {
        if (rows.length >= 10) return rows.slice(0, 10);
        return [...rows, ...Array.from({ length: 10 - rows.length }, () => ({ name: '—', score: null as any }))];
    }

    private clone<T>(v: T): T {
        return JSON.parse(JSON.stringify(v));
    }

    private load(): Leaderboards | null {
        try {
            const raw = localStorage.getItem(STORAGE_KEY);
            return raw ? (JSON.parse(raw) as Leaderboards) : null;
        } catch {
            return null;
        }
    }

    private save(state: Leaderboards) {
        try {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
        } catch {
            // ignore quota errors
        }
    }

    // sync multiple tabs
    constructor() {
        window.addEventListener('storage', (e) => {
            if (e.key === STORAGE_KEY && e.newValue) {
                try { this.state$.next(JSON.parse(e.newValue) as Leaderboards); } catch { }
            }
        });
    }
}
