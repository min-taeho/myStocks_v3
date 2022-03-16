import { DecimalPipe } from '@angular/common';
import { Component, OnInit, PipeTransform  } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

interface TradingNote {
  portfolioName: string;
  tradingDate: string;
  ticker: string;
  unitPrice: number;
  stockNum: number;
  tradingType: string;
  tradingAmount: number;
  fee: number;
  exchangeRate: number;
}

const TRADINGNOTES: TradingNote[] = [
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
  {
    portfolioName: '미국주식',
    tradingDate: '2022-01-13',
    ticker: 'MSFT',
    unitPrice: 318.00,
    stockNum: 15,
    tradingType: '매수',
    tradingAmount: 54770.00,
    fee: 11.93,
    exchangeRate: 1188.9
  },
];

function search(text: string, pipe: PipeTransform): TradingNote[] {
  return TRADINGNOTES.filter(tradingNote => {
    const term = text.toLowerCase();
    return tradingNote.portfolioName.toLowerCase().includes(term)
      || tradingNote.tradingDate.toLowerCase().includes(term)
      || tradingNote.ticker.toLowerCase().includes(term)
      || pipe.transform(tradingNote.unitPrice).includes(term)
      || pipe.transform(tradingNote.stockNum).includes(term)
      || tradingNote.tradingType.toLowerCase().includes(term)
      || pipe.transform(tradingNote.tradingAmount).includes(term)
      || pipe.transform(tradingNote.fee).includes(term)
      || pipe.transform(tradingNote.exchangeRate).includes(term)
      ;
  });
}

@Component({
  selector: 'app-trading-note-list',
  templateUrl: './trading-note-list.component.html',
  styleUrls: ['./trading-note-list.component.css'],
  providers: [DecimalPipe]
})
export class TradingNoteListComponent implements OnInit {

  tradingNotes$: Observable<TradingNote[]>;
  filter = new FormControl('');

  constructor(pipe: DecimalPipe) {
    this.tradingNotes$ = this.filter.valueChanges.pipe(
      startWith(''),
      map(text => search(text, pipe))
    );   
  }

  ngOnInit(): void {
  }

}
