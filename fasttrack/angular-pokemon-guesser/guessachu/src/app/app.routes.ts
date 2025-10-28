import { Routes } from '@angular/router';
import { firstRunGuard } from './first-run.guard';
export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        canMatch: [firstRunGuard],
        loadComponent: () =>
            import('./components/home/home').then(m => m.Home),
    },
    {
        path: 'config',
        loadComponent: () =>
            import('./components/configuration/configuration').then(m => m.Configuration),
    },
    {
        path: 'game',
        loadComponent: () =>
            import('./components/gameview/gameview').then(m => m.Gameview),
    },
    {
        path: 'hint',
        loadComponent: () =>
            import('./components/hint/hint').then(m => m.Hint),
    },
    {
        path: 'leaderboard',
        loadComponent: () =>
            import('./components/leaderboard/leaderboard').then(m => m.Leaderboard),
    },
    {
        path: 'save-score',
        loadComponent: () =>
            import('./components/savescore/savescore').then(m => m.Savescore),
    },
    {
        path: 'try-again',
        loadComponent: () =>
            import('./components/tryagain/tryagain').then(m => m.Tryagain),
    },
    { path: '**', redirectTo: '' },
];