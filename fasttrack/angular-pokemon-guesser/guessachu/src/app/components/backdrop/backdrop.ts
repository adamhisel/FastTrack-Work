import { Component, inject, HostListener, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

type BallItem = { name: string; sprites: { default: string } };

@Component({
  selector: 'app-backdrop',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './backdrop.html',
  styleUrls: ['./backdrop.css'],
})
export class Backdrop implements AfterViewInit {
  private http = inject(HttpClient);

  masterBall: BallItem | null = null;
  greatBall: BallItem | null = null;
  ultraBall: BallItem | null = null;
  pokeBall: BallItem | null = null;

  error = '';
  columns: Array<{ x: string; stack: string[] }> = [];

  private BALL_SIZE = 64;   // px (matches .pokeball-rain img width/height)
  private V_GAP = 20;   // px (matches .stream gap)

  ngOnInit() {
    this.fetchGreatBall();
    this.fetchMasterBall();
    this.fetchPokeBall();
    this.fetchUltraBall();
  }

  ngAfterViewInit() {
    const root = document.querySelector('.pokeball-rain');
    if (root) {
      const cs = getComputedStyle(root as Element);
      const fromVar = parseInt(cs.getPropertyValue('--ball-size'));
      if (!Number.isNaN(fromVar)) this.BALL_SIZE = fromVar;
    }
  }

  /** Recompute layout on resize/orientation change */
  @HostListener('window:resize')
  onResize() { this.maybeBuildColumns(); }

  private maybeBuildColumns() {
    if (!this.pokeBall || !this.greatBall || !this.ultraBall || !this.masterBall) return;

    const sources = [
      this.pokeBall.sprites?.default,
      this.greatBall.sprites?.default,
      this.ultraBall.sprites?.default,
      this.masterBall.sprites?.default,
    ].filter(Boolean) as string[];

    const cols = this.computeCols(window.innerWidth);
    const stackLen = this.computeStackLen(window.innerHeight);

    const padPct = (this.BALL_SIZE / 2) / window.innerWidth * 100;
    const step = cols > 1 ? (100 - 2 * padPct) / (cols - 1) : 0;


    const pick = () => sources[Math.floor(Math.random() * sources.length)];

    this.columns = Array.from({ length: cols }, (_, i) => ({
      x: `${padPct + i * step}%`,
      stack: Array.from({ length: stackLen }, pick),
    }));
  }

  /** Decide how many columns fit the viewport width */
  private computeCols(vw: number) {
    const LANE = this.BALL_SIZE * 1.8;
    const est = Math.floor(vw / LANE);
    return this.clamp(est, 3, 10);
  }

  /** Build a base stack tall enough so that 2×stack scrolls seamlessly */
  private computeStackLen(vh: number) {
    const perItem = this.BALL_SIZE + this.V_GAP;
    const needed = Math.ceil(vh / perItem) + 2;
    return this.clamp(needed, 8, 24);
  }

  private clamp(n: number, lo: number, hi: number) {
    return Math.max(lo, Math.min(hi, n));
  }


  fetchMasterBall() {
    this.http.get<BallItem>('https://pokeapi.co/api/v2/item/master-ball').subscribe({
      next: (data) => { this.masterBall = data; this.maybeBuildColumns(); },
      error: () => { this.error = 'Ball not found!'; },
    });
  }
  fetchGreatBall() {
    this.http.get<BallItem>('https://pokeapi.co/api/v2/item/great-ball').subscribe({
      next: (data) => { this.greatBall = data; this.maybeBuildColumns(); },
      error: () => { this.error = 'Ball not found!'; },
    });
  }
  fetchPokeBall() {
    this.http.get<BallItem>('https://pokeapi.co/api/v2/item/poke-ball').subscribe({
      next: (data) => { this.pokeBall = data; this.maybeBuildColumns(); },
      error: () => { this.error = 'Ball not found!'; },
    });
  }
  fetchUltraBall() {
    this.http.get<BallItem>('https://pokeapi.co/api/v2/item/ultra-ball').subscribe({
      next: (data) => { this.ultraBall = data; this.maybeBuildColumns(); },
      error: () => { this.error = 'Ball not found!'; },
    });
  }
}
