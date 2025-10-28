import { Component, inject, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../../config.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(private http: HttpClient) { }
  private config = inject(ConfigService);
  difficulty$ = this.config.difficulty;
  masterBall: any = null;
  greatBall: any = null;
  ultraBall: any = null;
  pokeBall: any = null;
  error: string = '';

  ngOnInit() {
    this.fetchGreatBall();
    this.fetchMasterBall();
    this.fetchPokeBall();
    this.fetchUltraBall();
  }


  fetchMasterBall(){
    const url = `https://pokeapi.co/api/v2/item/master-ball`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.masterBall = data;
      },
      error: () => {
        this.error = 'Ball not found!';
      },
    });
  }

  fetchGreatBall(){
    const url = `https://pokeapi.co/api/v2/item/great-ball`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.greatBall = data;
      },
      error: () => {
        this.error = 'Ball not found!';
      },
    });
  }

  fetchPokeBall(){
    const url = `https://pokeapi.co/api/v2/item/poke-ball`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.pokeBall = data;
      },
      error: () => {
        this.error = 'Ball not found!';
      },
    });
  }

  fetchUltraBall(){
    const url = `https://pokeapi.co/api/v2/item/ultra-ball`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.ultraBall = data;
      },
      error: () => {
        this.error = 'Ball not found!';
      },
    });
  }
}
