import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradingNoteListComponent } from './trading-note-list.component';

describe('TradingNoteListComponent', () => {
  let component: TradingNoteListComponent;
  let fixture: ComponentFixture<TradingNoteListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradingNoteListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradingNoteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
