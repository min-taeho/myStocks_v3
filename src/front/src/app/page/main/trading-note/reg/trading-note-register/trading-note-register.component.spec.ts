import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradingNoteRegisterComponent } from './trading-note-register.component';

describe('TradingNoteRegisterComponent', () => {
  let component: TradingNoteRegisterComponent;
  let fixture: ComponentFixture<TradingNoteRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradingNoteRegisterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradingNoteRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
