import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tryagain } from './tryagain';

describe('Tryagain', () => {
  let component: Tryagain;
  let fixture: ComponentFixture<Tryagain>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Tryagain]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Tryagain);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
