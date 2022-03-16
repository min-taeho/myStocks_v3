import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradingNoteComponent } from './trading-note.component';

describe('TradingNoteComponent', () => {
  let component: TradingNoteComponent;
  let fixture: ComponentFixture<TradingNoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradingNoteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradingNoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
