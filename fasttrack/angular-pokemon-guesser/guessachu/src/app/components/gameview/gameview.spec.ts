import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Gameview } from './gameview';

describe('Gameview', () => {
  let component: Gameview;
  let fixture: ComponentFixture<Gameview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Gameview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Gameview);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
