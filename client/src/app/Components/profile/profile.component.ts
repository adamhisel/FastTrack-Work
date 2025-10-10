import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  @Input() username!: string;
  @Input() name!: string;
  @Input() location!: string;
  @Input() bio!: string;
  @Input() avatarUrl!: string;
  @Input() titles!: string[];
  @Input() favoriteLanguage!: string;
  @Input() publicRepos!: number;
  @Input() totalStars!: number;
  @Input() highestStarred!: number;
  @Input() perfectRepos!: number;
  @Input() followers!: number;
  @Input() following!: number;

  @Input() publicReposBool: boolean | null = null;
  @Input() totalStarsBool: boolean | null = null;
  @Input() highestStarredBool: boolean | null = null;
  @Input() perfectReposBool: boolean | null = null;
  @Input() followersBool: boolean | null = null;
  @Input() followingBool: boolean | null = null;


  constructor() { }

  ngOnInit(): void {
  }
}
