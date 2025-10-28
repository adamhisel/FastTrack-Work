import { Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';     
import { ConfigService } from '../../config.service';
import { LeaderboardService } from '../../leaderboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-savescore',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './savescore.html',
  styleUrl: './savescore.css',
})
export class Savescore {
  private config = inject(ConfigService);
  private ld = inject(LeaderboardService);
  private router = inject(Router);

  difficulty = this.config.difficultyValue;
  level: 'beginner' | 'experienced' | 'master' | 'champion' = 'beginner';
  errorMessage = false;
  name = '';
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
    this.accuracy = this.numQuestions ? (this.score / this.numQuestions) * 100 : 0;
  }

  saveScore() {
    const name = this.name.trim();
    if (!name) {
      this.errorMessage = true;
      return;
    }
    this.errorMessage = false;
    this.ld.submit(this.difficulty, this.name, this.score);
    this.router.navigate(['/leaderboard']);
  }
}
