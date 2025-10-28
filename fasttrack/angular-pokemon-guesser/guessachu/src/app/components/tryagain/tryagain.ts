import { Component, inject} from '@angular/core';
import { ConfigService } from '../../config.service';
import { LeaderboardService } from '../../leaderboard.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tryagain',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tryagain.html',
  styleUrl: './tryagain.css',
})
export class Tryagain {
  private config = inject(ConfigService);
  private ld = inject(LeaderboardService);
  private router = inject(Router);

  difficulty = this.config.difficultyValue;
  level: 'beginner' | 'experienced' | 'master' | 'champion' = 'beginner';
  score = 0;
  numQuestions = 0;
  accuracy = 0;

  ngOnInit() {
    const { score = 0, numQuestions = 0 } = history.state || {};
    this.score = score;
    this.numQuestions = numQuestions;
    this.getAccuracy();

    // match image backdrop to level
    const difficultyMap = {
      'Beginner Trainer': 'beginner',
      'Experienced Trainer': 'experienced',
      'Pokemon Master': 'master',
      'Champion Mode': 'champion',
    } as const;

    this.level = difficultyMap[this.difficulty as keyof typeof difficultyMap];
  }

  getAccuracy() {
    this.accuracy = this.numQuestions ? this.score / this.numQuestions : 0;
  }

  tryAgain() {
    this.router.navigate(['/game']);
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
