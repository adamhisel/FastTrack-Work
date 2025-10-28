import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Savescore } from './savescore';

describe('Savescore', () => {
  let component: Savescore;
  let fixture: ComponentFixture<Savescore>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Savescore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Savescore);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
