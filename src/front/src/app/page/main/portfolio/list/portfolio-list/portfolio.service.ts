import {Injectable, PipeTransform} from '@angular/core';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
//import { HttpClient, HttpResponse } from '@angular/common/http';

import {Portfolio} from './portfolio';
import {PORTFOLIOS} from './portfolios';

import {DecimalPipe} from '@angular/common';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';
import {SortColumn, SortDirection} from './portfolio-list.sortable.directive';

interface SearchResult {
  portfolios: Portfolio[];
  total: number;
}

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(portfolios: Portfolio[], column: SortColumn, direction: string): Portfolio[] {
  if (direction === '' || column === '') {
    return portfolios;
  } else {
    return [...portfolios].sort((a, b) => {
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


function matches(portfolio: Portfolio, term: string, pipe: PipeTransform) {
  return portfolio.sector.toLowerCase().includes(term.toLowerCase())
    || portfolio.ticker.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(portfolio.currentPrice).includes(term)
    || pipe.transform(portfolio.declineRate).includes(term)
    || portfolio.w52.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(portfolio.unitPrice).includes(term)
    || pipe.transform(portfolio.stockNum).includes(term)
    || pipe.transform(portfolio.totalTradingAmount).includes(term)
    || pipe.transform(portfolio.evalAmount).includes(term)
    || pipe.transform(portfolio.earningAmount).includes(term)
    || pipe.transform(portfolio.earningRate).includes(term)
    || pipe.transform(portfolio.annualPayout).includes(term)
    || pipe.transform(portfolio.totalPayout).includes(term)
    || pipe.transform(portfolio.investmentDivYield).includes(term)
    || portfolio.dividendPayMonth.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(portfolio.portion).includes(term)
    ;
}

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _portfolios$ = new BehaviorSubject<Portfolio[]>([]);
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
      this._portfolios$.next(result.portfolios);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get portfolios$() { return this._portfolios$.asObservable(); }
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
    let portfolios = sort(PORTFOLIOS, sortColumn, sortDirection);

    // 2. filter
    portfolios = portfolios.filter(portfolio => matches(portfolio, searchTerm, this.pipe));
    const total = portfolios.length;

    // 3. paginate
    portfolios = portfolios.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({portfolios, total});
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
