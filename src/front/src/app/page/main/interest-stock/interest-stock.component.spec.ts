import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestStockComponent } from './interest-stock.component';

describe('InterestStockComponent', () => {
  let component: InterestStockComponent;
  let fixture: ComponentFixture<InterestStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterestStockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
