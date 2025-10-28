import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

// hint interface
export interface HintData {
  gameplay: string;
  details: Record<string, string>;
}

@Component({
  selector: 'app-hint',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hint.html',
  styleUrl: './hint.css',
})
export class Hint {
  @Input() hint: HintData = { gameplay: '', details: {} };
  @Input() level: 'beginner' | 'experienced' | 'master' | 'champion' = 'beginner';
  @Input() toggleHint!: () => void; // toggles hint on/off
}
