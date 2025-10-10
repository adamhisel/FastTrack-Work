import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/user.service';


interface Profile {
  username: string,
  name: string,
  location: string,
  bio: string,
  avatar_url: string,
  titles: string[],
  "favorite-language": string,
  "public-repos": number,
  "total-stars": number,
  "highest-starred": number,
  "perfect-repos": number,
  followers: number,
  following: number
}

@Component({
  selector: 'app-duel',
  templateUrl: './duel.component.html',
  styleUrls: ['./duel.component.css']
})
export class DuelComponent implements OnInit {
  usernameOne: string = ""
  usernameTwo: string = ""

  profilesShown: boolean = false;
  profiles: Profile[] = [];
  winningAttributesP1: Record<string, boolean> = {};
  winningAttributesP2: Record<string, boolean> = {};
  loading: boolean = false;
  numericKeys: string[] = ['public-repos', 'total-stars', 'highest-starred', 'perfect-repos', 'followers', 'following'];
  tiebreaker: number = 0;
  winner: string = "";
  errorMessage: string | null = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  receiveUsernameOne(valueEmitted: string) {
    this.usernameOne = valueEmitted;
  }

  receiveUsernameTwo(valueEmitted: string) {
    this.usernameTwo = valueEmitted;
  }
  onSubmit() {
    this.loading = true;
    this.userService.duelUsers(this.usernameOne, this.usernameTwo)
      .then((data: any) => {
        if (!data) {
          this.profiles = []
          return;
        }

        const arr: any[] = Array.isArray(data) ? data : [data]
        this.profiles = arr.map(item => ({
          username: item.username,
          name: item.name,
          location: item.location,
          bio: item.bio,
          "avatar_url": item["avatar_url"],
          titles: item.titles ?? [],
          "favorite-language": item["favorite-language"],
          "public-repos": item["public-repos"],
          "total-stars": item["total-stars"],
          "highest-starred": item["highest-starred"],
          "perfect-repos": item["perfect-repos"],
          followers: item.followers,
          following: item.following
        } as Profile));

        if (this.profiles.length === 2) {
          this.decideWinner();
        }
        this.profilesShown = true;
      })

      .catch(err => {
        this.loading = false;
        console.error(err);
        if(this.usernameOne === "" && this.usernameTwo === ""){
          this.errorMessage = "Please enter two usernames to duel.";
        }
        else if(this.usernameOne === "" || this.usernameTwo === ""){
          this.errorMessage = "Please enter another username to duel.";
        }
        else if (err.status === 404) {
          this.errorMessage = "One or both usernames now found. Please check the usernames.";
        } 
        else {
          this.errorMessage = "Something went wrong. Please try again.";
        }
        this.profilesShown = false;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  decideWinner() {
    if (this.profiles.length < 2) {
      this.winningAttributesP1 = {};
      this.winningAttributesP2 = {};
      return;
    }
    const [p1, p2] = this.profiles;
    const resultP1: Record<string, boolean> = {};
    const resultP2: Record<string, boolean> = {};
    for (const key of this.numericKeys) {
      const v1 = (p1 as any)[key];
      const v2 = (p2 as any)[key];
      if (v1 !== v2) {
        resultP1[key] = v1 > v2;
        resultP2[key] = v1 < v2;
      }
      else {
        resultP1[key] = false;
        resultP2[key] = false;
      }
    }
    this.winningAttributesP1 = resultP1;
    this.winningAttributesP2 = resultP2;

    this.tallyWinner();
  }

  tallyWinner() {
    let p1Count = 0;
    let p2Count = 0;
    for (const key of this.numericKeys) {
      if (this.winningAttributesP1[key]) {
        p1Count++;
      } else if (this.winningAttributesP2[key]) {
        p2Count++;
      }
    }
    if (p1Count === p2Count) {
      this.winner = "It's A Draw!";
    }
    else {
      this.winner = p1Count > p2Count ? this.usernameOne : this.usernameTwo;
    }
  }

  startOver() {
    this.profilesShown = false;
    this.errorMessage = "";
    this.profiles = []
  }
}
