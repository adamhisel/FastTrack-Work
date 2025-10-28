import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Difficulty, LeaderboardService } from '../../leaderboard.service';
import { ConfigService } from '../../config.service';
import { map, switchMap } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leaderboard',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './leaderboard.html',
  styleUrl: './leaderboard.css',
})
export class Leaderboard {
  private lb = inject(LeaderboardService);
  private config = inject(ConfigService);
  top10$ = this.config.difficulty.pipe(
    map((d) => d as Difficulty),
    switchMap((d) => this.lb.boardPadded$(d))
  );

  config$ = this.config.difficulty;
  difficulty = this.config.difficultyValue;
  level: 'beginner' | 'experienced' | 'master' | 'champion' = 'beginner';

  ngOnInit() {
    // match image backdrop to level
    const difficultyMap = {
      'Beginner Trainer': 'beginner',
      'Experienced Trainer': 'experienced',
      'Pokemon Master': 'master',
      'Champion Mode': 'champion',
    } as const;

    this.level = difficultyMap[this.difficulty as keyof typeof difficultyMap];
  }
}
