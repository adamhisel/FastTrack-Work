import { inject } from '@angular/core';
import { CanMatchFn, Router } from '@angular/router';

export const firstRunGuard: CanMatchFn = () => {
  const router = inject(Router);
  const KEY = 'seenDifficultyOnce';

  if (!localStorage.getItem(KEY)) {
    localStorage.setItem(KEY, '1');                   
    return router.createUrlTree(['/config']);       
  }
  return true;                                      
};
