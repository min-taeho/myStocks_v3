import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestStockListComponent } from './interest-stock-list.component';

describe('InterestStockListComponent', () => {
  let component: InterestStockListComponent;
  let fixture: ComponentFixture<InterestStockListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterestStockListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestStockListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
