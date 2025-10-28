import { Component, inject, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Hint } from '../hint/hint';
import { PokemonQuestion, pokemonByLevel } from '../../../pokemon-data';
import { Beginner } from './beginner/beginner';
import { Experienced } from './experienced/experienced';
import { Master } from './master/master';
import { Champion } from './champion/champion';
import { ConfigService } from '../../config.service';
import { LeaderboardService, Difficulty } from '../../leaderboard.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-gameview',
  standalone: true,
  imports: [Hint, RouterLink, CommonModule, FormsModule, Beginner, Experienced, Master, Champion],
  templateUrl: './gameview.html',
  styleUrls: ['./gameview.css'],
})
export class Gameview implements OnInit {
  private config = inject(ConfigService);
  private lb = inject(LeaderboardService);
  private router = inject(Router);
  level: 'beginner' | 'experienced' | 'master' | 'champion' = 'beginner';

  pokemonName = '';
  pokemon: any = null;
  loading = false;
  error = '';
  message = ''; // TODO: Im thinking of making this a modal object to display modal components
  hintData: { gameplay: string; details: Record<string, string> } = { gameplay: '', details: {} };
  score = 0;
  currentIndex = 0;
  showHint = false;
  currentOptions: string[] = [];
  feedbackState: 'correct' | 'wrong' | null = null;
  selectedOptionClass: Record<string, string> = {};
  pokemonByLevel: Record<'beginner' | 'experienced' | 'master' | 'champion', PokemonQuestion[]> =
    pokemonByLevel;

  constructor(private http: HttpClient) {}

  // initialize the game
  ngOnInit() {
    // Map the difficulty string from ConfigService to the union type
    const difficultyMap = {
      'Beginner Trainer': 'beginner',
      'Experienced Trainer': 'experienced',
      'Pokemon Master': 'master',
      'Champion Mode': 'champion',
    } as const;

    // Subscribe to ConfigService difficulty updates
    this.config.difficulty.subscribe((d) => {
      if (d in difficultyMap) {
        this.level = difficultyMap[d as keyof typeof difficultyMap]; // literal union type
        this.initializeQuestions(); // Initialize fresh randomized questions
      }
    });
  }

  // load the next Pokémon
  loadNextPokemon() {
    if (this.currentIndex >= this.pokemonByLevel[this.level].length) {
      this.pokemon = null;
      this.endLevel();
      return;
    }

    this.pokemonName = '';
    this.message = '';
    this.loading = true;
    this.selectedOptionClass = {};
    const currentQuestion = this.pokemonByLevel[this.level][this.currentIndex];
    this.currentOptions = currentQuestion.options;

    // Fetch pokemon data
    const url = `https://pokeapi.co/api/v2/pokemon/${currentQuestion.name.toLowerCase()}`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.pokemon = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Pokémon not found!';
        this.loading = false;
      },
    });
  }

  endLevel() {
    const finalScore = this.score;
    const numQuestions = this.pokemonByLevel[this.level].length;
    const diff = this.config.difficultyValue as Difficulty;

    const qualifies = this.lb.wouldQualify(diff, finalScore);

    this.router.navigate([qualifies ? '/save-score' : '/try-again'], {
      state: { score: finalScore, numQuestions },
    });
  }

  // check the answer
  checkAnswer(selectedOption?: string) {
    if (!this.pokemon) return;

    const correctName = this.pokemonByLevel[this.level][this.currentIndex].name.toLowerCase();
    const guess = (selectedOption ?? this.pokemonName).trim().toLowerCase();

    // Reset highlights - Green for correct & Red for wrong
    this.currentOptions.forEach((opt) => (this.selectedOptionClass[opt] = ''));

    // TODO: Add scoring based on pokemon index ie index 0 = 1 point, index 3 = 4 points etc
    if (guess === correctName) {
      if (selectedOption) this.selectedOptionClass[selectedOption] = 'correct';
      this.score++;
      // this.message = 'Correct!';
      this.feedbackState = 'correct';
    } else {
      if (selectedOption) this.selectedOptionClass[selectedOption] = 'wrong';
      // this.message = `Incorrect! It was ${this.pokemon.name}`;
      this.feedbackState = 'wrong';
    }

    this.pokemonName = '';

    setTimeout(() => {
      this.feedbackState = null;
      this.message = '';
      this.currentIndex++;
      this.loadNextPokemon();
    }, 1000);
  }

  // get the class for the option button - used to add styling to guessed option
  getOptionClass(option: string) {
    return this.selectedOptionClass[option] || '';
  }

  // toggle the hint
  toggleHint() {
    this.showHint = !this.showHint;
    this.hintData = this.showHint ? this.getHint() : { gameplay: '', details: {} };
  }

  // get hints based on game level
  getHint(): { gameplay: string; details: Record<string, string> } {
    if (!this.pokemon) return { gameplay: '', details: {} };

    const name = this.pokemon.name;
    const ability = this.pokemon.abilities[0]?.ability.name || 'unknown';
    const type = this.pokemon.types.map((t: any) => t.type.name).join(', ') || 'unknown';
    const height = this.pokemon.height;
    const weight = this.pokemon.weight;
    const baseStatSum =
      this.pokemon.stats?.reduce((sum: number, s: any) => sum + s.base_stat, 0) || 'unknown';
    const cry = this.pokemon.cries?.latest || 'unknown';

    // Generate a name hint (3 random characters from the Pokémon name)
    // Gives master and champion users a little hint
    const generateNameHint = (name: string) => {
      const chars = name.split('');
      const uniqueIndices = new Set<number>(); // ensure no duplicate letters

      // Randomly select 3 unique indices
      while (uniqueIndices.size < 3 && uniqueIndices.size < chars.length) {
        uniqueIndices.add(Math.floor(Math.random() * chars.length));
      }

      // Generate the hint string
      const hint = Array.from(uniqueIndices)
        .sort((a, b) => a - b)
        .map((i) => chars[i])
        .join('');
      return hint.toUpperCase();
    };

    const nameHint = generateNameHint(name);

    let gameplayHint = '';
    let details: Record<string, string> = {};

    // Set gameplay and details based on game level
    switch (this.level) {
      case 'beginner':
        gameplayHint = 'You have plenty of hints! Use the options to guess the Pokémon correctly.';
        details = {
          Type: type,
          Ability: ability,
          Height: height.toString(),
          Weight: weight.toString(),
          'Base Stats Total': baseStatSum.toString(),
          Cry: cry,
        };
        break;

      case 'experienced':
        gameplayHint = 'Hints are fewer; pay attention to type, ability, and Pokémon cry.';
        details = {
          Type: type,
          Ability: ability,
          Cry: cry,
        };
        break;

      case 'master':
        gameplayHint =
          'Hints are minimal; focus on type, Pokémon cry, and a few letters from the name.';
        details = {
          Type: type,
          Cry: cry,
          'Name Hint': nameHint,
        };
        break;

      case 'champion':
        gameplayHint = 'Only the Pokémon cry and a few random letters from its name are revealed';
        details = {
          Cry: cry,
          'Name Hint': nameHint,
        };
        break;
    }

    // Return hint data
    return { gameplay: gameplayHint, details };
  }

  // number of questions left
  questionsLeft() {
    return this.pokemonByLevel[this.level].length - this.currentIndex;
  }

  // Initialize fresh questions with shuffled questions and options
  initializeQuestions() {
    const originalQuestions = this.pokemonByLevel[this.level];

    // Shuffle the options for each question
    const questionsWithShuffledOptions = originalQuestions.map((q) => ({
      ...q,
      options: this.shuffleArray(q.options),
    }));

    // shuffle the questions then assign the shuffled questions to the current level
    this.pokemonByLevel[this.level] = this.shuffleArray(questionsWithShuffledOptions);

    this.currentIndex = 0;
    this.loadNextPokemon();
  }

  // Shuffle an array without mutating the original
  shuffleArray<T>(array: T[]): T[] {
    const copy = [...array];
    for (let i = copy.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [copy[i], copy[j]] = [copy[j], copy[i]];
    }

    // Return the shuffled array
    return copy;
  }
}
