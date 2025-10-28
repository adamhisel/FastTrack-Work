import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HintData } from '../../hint/hint';

@Component({
  selector: 'app-champion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './champion.html',
  styleUrl: './champion.css',
})
export class Champion {
  @Input() pokemonName = '';
  @Input() showHint = false;
  @Input() hint: HintData = { gameplay: '', details: {} };
  @Input() cry = '';
  @Input() feedbackState: 'correct' | 'wrong' | null = null;

  // emit data and events to parent component
  @Output() toggleHint = new EventEmitter<void>();
  @Output() answerEntered = new EventEmitter<string>();

  // submit the guess
  submitGuess() {
    if (!this.pokemonName.trim()) return;
    this.answerEntered.emit(this.pokemonName.trim());
    this.pokemonName = ''; // reset input after guessing
  }
}
