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
    this.profilesShown = true;
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
      })

      .catch(err => console.error(err))
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
    for (const key of this.numericKeys) {
      const v1 = (p1 as any)[key];
      const v2 = (p1 as any)[key];
      resultP1[key] = v1 > v2;
    }
    this.winningAttributesP1 = resultP1
  }

  startOver() {
    this.profilesShown = false;
    this.profiles = []
  }
}
