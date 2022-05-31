import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { StockService } from '../service/stock.service';

@Component({
  selector: 'app-fair-price-figure',
  templateUrl: './fair-price-figure.component.html',
  styleUrls: ['./fair-price-figure.component.css']
})
export class FairPriceFigureComponent implements OnInit {

  @Input() ticker!: string;

  model = {
    currentPrice: 0,
    highestPrice: 0,
    lowerPrice: 0,

    segment75: 0,
    segment50: 0,
    segment25: 0,

    seg100_down75: 0,
    seg100_down50: 0,
    seg100_down25: 0,

    seg75_down75: 0,
    seg75_down50: 0,
    seg75_down25: 0,

    seg50_down75: 0,
    seg50_down50: 0,
    seg50_down25: 0,

    seg25_down75: 0,
    seg25_down50: 0,
    seg25_down25: 0,    

    rise_rate1: 0,
    rise_rate2: 0,
    rise_rate3: 0,
    rise_rate4: 0,
    rise_rate5: 0,
    rise_rate6: 0,
    rise_rate7: 0,
    rise_rate8: 0,
    rise_rate9: 0,
    rise_rate10: 0,
    rise_rate11: 0,
    rise_rate12: 0,
    rise_rate13: 0,
    rise_rate14: 0,
    rise_rate15: 0,
    rise_rate16: 0,
    rise_rate17: 0,

    drop_rate1: 0,
    drop_rate2: 0,
    drop_rate3: 0,
    drop_rate4: 0,
    drop_rate5: 0,
    drop_rate6: 0,
    drop_rate7: 0,
    drop_rate8: 0,
    drop_rate9: 0,
    drop_rate10: 0,
    drop_rate11: 0,
    drop_rate12: 0,
    drop_rate13: 0,
    drop_rate14: 0,
    drop_rate15: 0,
    drop_rate16: 0
  };

  showPoint = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];

  constructor(private service: StockService) {
  }

  ngOnInit(): void {
    this.service.getStockDetail(this.ticker)
                .subscribe(item => {
                  console.log(item);
                  this.model.currentPrice = item.currentPrice;

                  this.model.highestPrice = item.highestPrice;
                  this.model.lowerPrice = item.lowerPrice;

                  this.model.segment75 = this.calcSegment(0.75, this.model.highestPrice, this.model.lowerPrice);
                  this.model.segment50 = this.calcSegment(0.50, this.model.highestPrice, this.model.lowerPrice);
                  this.model.segment25 = this.calcSegment(0.25, this.model.highestPrice, this.model.lowerPrice);

                  this.model.seg100_down75 = this.calcSegment(0.75, this.model.highestPrice, this.model.segment75);
                  this.model.seg100_down50 = this.calcSegment(0.50, this.model.highestPrice, this.model.segment75);
                  this.model.seg100_down25 = this.calcSegment(0.25, this.model.highestPrice, this.model.segment75);

                  this.model.seg75_down75 = this.calcSegment(0.75, this.model.segment75, this.model.segment50);
                  this.model.seg75_down50 = this.calcSegment(0.50, this.model.segment75, this.model.segment50);
                  this.model.seg75_down25 = this.calcSegment(0.25, this.model.segment75, this.model.segment50);

                  this.model.seg50_down75 = this.calcSegment(0.75, this.model.segment50, this.model.segment25);
                  this.model.seg50_down50 = this.calcSegment(0.50, this.model.segment50, this.model.segment25);
                  this.model.seg50_down25 = this.calcSegment(0.25, this.model.segment50, this.model.segment25);

                  this.model.seg25_down75 = this.calcSegment(0.75, this.model.segment25, this.model.lowerPrice);
                  this.model.seg25_down50 = this.calcSegment(0.50, this.model.segment25, this.model.lowerPrice);
                  this.model.seg25_down25 = this.calcSegment(0.25, this.model.segment25, this.model.lowerPrice);

                  this.model.rise_rate1 = this.showRiseRate(this.model.highestPrice);
                  this.model.rise_rate2 = this.showRiseRate(this.model.seg100_down75);
                  this.model.rise_rate3 = this.showRiseRate(this.model.seg100_down50);
                  this.model.rise_rate4 = this.showRiseRate(this.model.seg100_down25);
                  this.model.rise_rate5 = this.showRiseRate(this.model.segment75);
                  this.model.rise_rate6 = this.showRiseRate(this.model.seg75_down75);
                  this.model.rise_rate7 = this.showRiseRate(this.model.seg75_down50);
                  this.model.rise_rate8 = this.showRiseRate(this.model.seg75_down25);
                  this.model.rise_rate9 = this.showRiseRate(this.model.segment50);
                  this.model.rise_rate10 = this.showRiseRate(this.model.seg50_down75);
                  this.model.rise_rate11 = this.showRiseRate(this.model.seg50_down50);
                  this.model.rise_rate12 = this.showRiseRate(this.model.seg50_down25);
                  this.model.rise_rate13 = this.showRiseRate(this.model.segment25);
                  this.model.rise_rate14 = this.showRiseRate(this.model.seg25_down75);
                  this.model.rise_rate15 = this.showRiseRate(this.model.seg25_down50);
                  this.model.rise_rate16 = this.showRiseRate(this.model.seg25_down25);
                  this.model.rise_rate17 = this.showRiseRate(this.model.lowerPrice);
                  
                  this.model.drop_rate1 = this.showDropRate(this.model.seg100_down75);
                  this.model.drop_rate2 = this.showDropRate(this.model.seg100_down50);
                  this.model.drop_rate3 = this.showDropRate(this.model.seg100_down25);
                  this.model.drop_rate4 = this.showDropRate(this.model.segment75);
                  this.model.drop_rate5 = this.showDropRate(this.model.seg75_down75);
                  this.model.drop_rate6 = this.showDropRate(this.model.seg75_down50);
                  this.model.drop_rate7 = this.showDropRate(this.model.seg75_down25);
                  this.model.drop_rate8 = this.showDropRate(this.model.segment50);
                  this.model.drop_rate9 = this.showDropRate(this.model.seg50_down75);
                  this.model.drop_rate10 = this.showDropRate(this.model.seg50_down50);
                  this.model.drop_rate11 = this.showDropRate(this.model.seg50_down25);
                  this.model.drop_rate12 = this.showDropRate(this.model.segment25);
                  this.model.drop_rate13 = this.showDropRate(this.model.seg25_down75);
                  this.model.drop_rate14 = this.showDropRate(this.model.seg25_down50);
                  this.model.drop_rate15 = this.showDropRate(this.model.seg25_down25);
                  this.model.drop_rate16 = this.showDropRate(this.model.lowerPrice);                  

                  this.showCurrentPoint();
                });
  }

  private calcSegment(rate: number, basis1: number, basis2: number) {
    return ((basis1 - basis2) * rate) + basis2;
  }

  private showDropRate(value: number): number {
    return (value/this.model.highestPrice - 1);
  }
  private showRiseRate(value: number): number {
    return (value/this.model.lowerPrice - 1);
  }
  private showCurrentPoint() {
    let index = 0;
    if ( this.numberBetween(this.model.currentPrice, this.model.highestPrice, this.model.seg100_down75, true) ) index = 0;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg100_down75, this.model.seg100_down50, true) ) index = 1;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg100_down50, this.model.seg100_down25, true) ) index = 2;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg100_down25, this.model.segment75, true) ) index = 3;
    else if ( this.numberBetween(this.model.currentPrice, this.model.segment75, this.model.seg75_down75, true) ) index = 4;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg75_down75, this.model.seg75_down50, true) ) index = 5;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg75_down50, this.model.seg75_down25, true) ) index = 6;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg75_down25, this.model.segment50, true) ) index = 7;
    else if ( this.numberBetween(this.model.currentPrice, this.model.segment50, this.model.seg50_down75, true) ) index = 8;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg50_down75, this.model.seg50_down50, true) ) index = 9;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg50_down50, this.model.seg50_down25, true) ) index = 10;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg50_down25, this.model.segment25, true) ) index = 11;
    else if ( this.numberBetween(this.model.currentPrice, this.model.segment25, this.model.seg25_down75, true) ) index = 12;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg25_down75, this.model.seg25_down50, true) ) index = 13;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg25_down50, this.model.seg25_down25, true) ) index = 14;
    else if ( this.numberBetween(this.model.currentPrice, this.model.seg25_down25, this.model.lowerPrice, true) ) index = 15;

    let tmpShowPoint = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];
    tmpShowPoint[index] = true;
    tmpShowPoint[index+1] = true;
    this.showPoint = tmpShowPoint;
  }
  private numberBetween(value: number, a: number, b: number, inclusive: boolean) {
    let min = Math.min.apply(Math, [a, b]), max = Math.max.apply(Math, [a, b]);
    return inclusive ? value >= min && value <= max : value > min && value < max;
  }


}
