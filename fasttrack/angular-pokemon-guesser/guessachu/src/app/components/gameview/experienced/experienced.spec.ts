import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Experienced } from './experienced';

describe('Experienced', () => {
  let component: Experienced;
  let fixture: ComponentFixture<Experienced>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Experienced]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Experienced);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
