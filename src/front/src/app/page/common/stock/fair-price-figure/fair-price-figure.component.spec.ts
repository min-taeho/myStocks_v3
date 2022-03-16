import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FairPriceFigureComponent } from './fair-price-figure.component';

describe('FairPriceFigureComponent', () => {
  let component: FairPriceFigureComponent;
  let fixture: ComponentFixture<FairPriceFigureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FairPriceFigureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FairPriceFigureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
