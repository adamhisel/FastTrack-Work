
// Pokemon question interface
export interface PokemonQuestion {
  name: string;
  options: string[];
}

// Pokemon questions by level
export const pokemonByLevel: Record<
  'beginner' | 'experienced' | 'master' | 'champion',
  PokemonQuestion[]
> = {
  beginner: [
    { name: 'Pikachu', options: ['Pikachu', 'Bulbasaur', 'Squirtle', 'Charmander', 'Eevee'] },
    {
      name: 'Charmander',
      options: ['Bulbasaur', 'Squirtle', 'Charmander', 'Pikachu', 'Jigglypuff'],
    },
    { name: 'Bulbasaur', options: ['Bulbasaur', 'Pikachu', 'Charmander', 'Squirtle', 'Meowth'] },
    { name: 'Squirtle', options: ['Squirtle', 'Eevee', 'Charmander', 'Jigglypuff', 'Snorlax'] },
    { name: 'Jigglypuff', options: ['Jigglypuff', 'Meowth', 'Psyduck', 'Eevee', 'Gyarados'] },
    { name: 'Meowth', options: ['Meowth', 'Pikachu', 'Bulbasaur', 'Charmander', 'Snorlax'] },
    { name: 'Psyduck', options: ['Psyduck', 'Squirtle', 'Gyarados', 'Eevee', 'Charmander'] },
    { name: 'Eevee', options: ['Eevee', 'Pikachu', 'Bulbasaur', 'Squirtle', 'Snorlax'] },
    { name: 'Snorlax', options: ['Snorlax', 'Gyarados', 'Jigglypuff', 'Charmander', 'Pikachu'] },
    { name: 'Gyarados', options: ['Gyarados', 'Psyduck', 'Snorlax', 'Eevee', 'Bulbasaur'] },
  ],
  experienced: [
    { name: 'Gengar', options: ['Gengar', 'Haunter', 'Alakazam', 'Lapras', 'Muk'] },
    { name: 'Alakazam', options: ['Alakazam', 'Gengar', 'Hypno', 'Kadabra', 'Mr. Mime'] },
    { name: 'Machamp', options: ['Machamp', 'Golem', 'Hitmonlee', 'Primeape', 'Poliwrath'] },
    { name: 'Lapras', options: ['Lapras', 'Gyarados', 'Vaporeon', 'Dewgong', 'Cloyster'] },
    { name: 'Scyther', options: ['Scyther', 'Pinsir', 'Beedrill', 'Kabutops', 'Farfetch’d'] },
    {
      name: 'Dragonite',
      options: ['Dragonite', 'Charizard', 'Aerodactyl', 'Gyarados', 'Salamence'],
    },
    { name: 'Arcanine', options: ['Arcanine', 'Ninetales', 'Rapidash', 'Flareon', 'Growlithe'] },
    { name: 'Golem', options: ['Golem', 'Onix', 'Graveler', 'Rhydon', 'Kabutops'] },
    { name: 'Electabuzz', options: ['Electabuzz', 'Jolteon', 'Raichu', 'Magmar', 'Ampharos'] },
    { name: 'Gyarados', options: ['Gyarados', 'Lapras', 'Seadra', 'Milotic', 'Tentacruel'] },
  ],

  master: [
    { name: 'Tyranitar', options: [] },
    { name: 'Salamence', options: [] },
    { name: 'Metagross', options: [] },
    { name: 'Garchomp', options: [] },
    { name: 'Hydreigon', options: [] },
    { name: 'Infernape', options: [] },
    { name: 'Lucario', options: [] },
    { name: 'Gardevoir', options: [] },
    { name: 'Togekiss', options: [] },
    { name: 'Garchomp', options: [] },
  ],
  champion: [
    { name: 'Mewtwo', options: [] },
    { name: 'Rayquaza', options: [] },
    { name: 'Lugia', options: [] },
    { name: 'Ho-Oh', options: [] },
    { name: 'Dialga', options: [] },
    { name: 'Palkia', options: [] },
    { name: 'jirachi', options: [] }, // giratina did not exist in the API database
    { name: 'darkrai', options: [] }, // zygarde did not exist in the API database
    { name: 'Reshiram', options: [] },
    { name: 'Zekrom', options: [] },
  ],
};
