import {Injectable, PipeTransform} from '@angular/core';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
//import { HttpClient, HttpResponse } from '@angular/common/http';

import {InterestStock} from './interest-stock';
import {INTERESTSTOCKS} from './interest-stocks';

import {DecimalPipe} from '@angular/common';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';
import {SortColumn, SortDirection} from './interestStock-list.sortable.directive';

interface SearchResult {
  interestStocks: InterestStock[];
  total: number;
}

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(interestStocks: InterestStock[], column: SortColumn, direction: string): InterestStock[] {
  if (direction === '' || column === '') {
    return interestStocks;
  } else {
    return [...interestStocks].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  sortColumn: SortColumn;
  sortDirection: SortDirection;
}

function matches(interestStock: InterestStock, term: string, pipe: PipeTransform) {
  return interestStock.sector.toLowerCase().includes(term.toLowerCase())
    || interestStock.ticker.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(interestStock.currentPrice).includes(term)
    || pipe.transform(interestStock.desiredPurchasePrice).includes(term)
    || pipe.transform(interestStock.declineRate).includes(term)
    || interestStock.under10Price.toLowerCase().includes(term.toLowerCase())
    || interestStock.w52.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(interestStock.dividendYield).includes(term)
    || pipe.transform(interestStock.fiveyearAvgDividendYield).includes(term)
    || pipe.transform(interestStock.payoutRatio).includes(term)
    || pipe.transform(interestStock.dividendGrowth).includes(term)
    || pipe.transform(interestStock.annualPayout).includes(term)
    || interestStock.dividendPayMonth.toLowerCase().includes(term.toLowerCase())
    ;
}

@Injectable({
  providedIn: 'root'
})
export class InterestStockService {

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _interestStocks$ = new BehaviorSubject<InterestStock[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  //private const PORTFOLIOS2: Portfolio[];

  private _state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  constructor(private pipe: DecimalPipe) {
    
    //this.getList();
    
    this._search$.pipe(
      tap(() => this._loading$.next(true)),
      debounceTime(200),
      switchMap(() => this._search()),
      delay(200),
      tap(() => this._loading$.next(false))
    ).subscribe(result => {
      this._interestStocks$.next(result.interestStocks);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get interestStocks$() { return this._interestStocks$.asObservable(); }
  get total$() { return this._total$.asObservable(); }
  get loading$() { return this._loading$.asObservable(); }
  get page() { return this._state.page; }
  get pageSize() { return this._state.pageSize; }
  get searchTerm() { return this._state.searchTerm; }

  set page(page: number) { this._set({page}); }
  set pageSize(pageSize: number) { this._set({pageSize}); }
  set searchTerm(searchTerm: string) { this._set({searchTerm}); }
  set sortColumn(sortColumn: SortColumn) { this._set({sortColumn}); }
  set sortDirection(sortDirection: SortDirection) { this._set({sortDirection}); }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }
  
  private _search(): Observable<SearchResult> {
    const {sortColumn, sortDirection, pageSize, page, searchTerm} = this._state;

    // 1. sort
    let interestStocks = sort(INTERESTSTOCKS, sortColumn, sortDirection);

    // 2. filter
    interestStocks = interestStocks.filter(interestStock => matches(interestStock, searchTerm, this.pipe));
    const total = interestStocks.length;

    // 3. paginate
    interestStocks = interestStocks.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({interestStocks, total});
  }  

  /*
  private getList(): void {
    // TODO 서버 API를 통해 포트폴리오 리스트 가져오기
    //this.PORTFOLIOS2 = '';
    
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('projectid', this.id);
    let params = new URLSearchParams();
    params.append("someParamKey", this.someParamValue);


    this.http.get<Portfolio[]>("http://localhost:9200/api/v1/portfolio/byEmailAndName", { headers: headers, search: params });
    
  }
  */

}
