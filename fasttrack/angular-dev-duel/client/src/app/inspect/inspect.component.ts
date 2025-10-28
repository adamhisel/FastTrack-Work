import { Component, Input, OnInit } from '@angular/core';
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
  selector: 'app-inspect',
  templateUrl: './inspect.component.html',
  styleUrls: ['./inspect.component.css']
})
export class InspectComponent implements OnInit {

  username: string = "";
  profileShown: boolean = false;
  profile!: Profile;
  loading: boolean = false;
  errorMessage: string | null = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  receiveUsername(valueEmitted: string) {
    this.username = valueEmitted;
  }

  onSubmit() {
    this.loading = true;
    this.userService.inspectUser(this.username)
      .then((data: any) => {
        this.profileShown = true;
        this.profile = data as Profile;
      })
      .catch(err => {
        this.loading = false;
        console.error(err);
        if(this.username === ""){
          this.errorMessage = "Please enter a username.";
        }
        else if (err.status === 404) {
          this.errorMessage = "User not found. Please check the username.";
        } 
        else {
          this.errorMessage = "Something went wrong. Please try again.";
        }
        this.profileShown = false;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  startOver() {
    this.profileShown = false;
    this.errorMessage = "";
    this.profile = {
      username: '',
      name: '',
      location: '',
      bio: '',
      'avatar_url': '',
      titles: [],
      'favorite-language': '',
      'public-repos': 0,
      'total-stars': 0,
      'highest-starred': 0,
      'perfect-repos': 0,
      followers: 0,
      following: 0
    };
  }
}
