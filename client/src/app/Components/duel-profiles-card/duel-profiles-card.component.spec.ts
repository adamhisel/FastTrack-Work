import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DuelProfilesCardComponent } from './duel-profiles-card.component';

describe('DuelProfilesCardComponent', () => {
  let component: DuelProfilesCardComponent;
  let fixture: ComponentFixture<DuelProfilesCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DuelProfilesCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DuelProfilesCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
