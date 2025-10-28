import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HintData } from '../../hint/hint';

@Component({
  selector: 'app-experienced',
  imports: [CommonModule, FormsModule],
  templateUrl: './experienced.html',
  styleUrl: './experienced.css',
})
export class Experienced {
  @Input() currentOptions: string[] = [];
  @Input() getOptionClass!: (option: string) => string;
  @Input() hint: HintData = { gameplay: '', details: {} };

  // emit the selected option to parent component
  @Output() answerSelected = new EventEmitter<string>(); 

  // select an option
  selectOption(option: string) {
    this.answerSelected.emit(option);
  }
}
