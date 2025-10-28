import { Component, inject} from '@angular/core';
import { ConfigService } from '../../config.service';
import { Router, RouterLink } from '@angular/router';
import { Difficulty } from '../../leaderboard.service';

@Component({
  selector: 'app-configuration',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './configuration.html',
  styleUrl: './configuration.css',
})
export class Configuration {
  private configData = inject(ConfigService);
  private router = inject(Router);

  pick(level: Difficulty) {
    this.configData.updateDifficulty(level);
    this.router.navigate(['/']);
  }
}
