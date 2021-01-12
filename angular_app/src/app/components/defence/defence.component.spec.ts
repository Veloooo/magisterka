import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefenceComponent } from './defence.component';

describe('DefenceComponent', () => {
  let component: DefenceComponent;
  let fixture: ComponentFixture<DefenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
