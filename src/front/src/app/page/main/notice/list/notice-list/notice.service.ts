import {Injectable, PipeTransform} from '@angular/core';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
//import { HttpClient, HttpResponse } from '@angular/common/http';

import {Notice} from './notice';
import {NOTICES} from './notices';

import {DecimalPipe} from '@angular/common';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';
import {SortColumn, SortDirection} from './notice-list.sortable.directive';

interface SearchResult {
  notices: Notice[];
  total: number;
}

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(notices: Notice[], column: SortColumn, direction: string): Notice[] {
  if (direction === '' || column === '') {
    return notices;
  } else {
    return [...notices].sort((a, b) => {
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


function matches(notice: Notice, term: string, pipe: PipeTransform) {
  return pipe.transform(notice.id).includes(term)
  || notice.title.toLowerCase().includes(term.toLowerCase())
  || notice.contents.toLowerCase().includes(term.toLowerCase())
  || notice.createdDate.toLowerCase().includes(term.toLowerCase())
    ;
}

@Injectable({
  providedIn: 'root'
})
export class NoticeService {

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _notices$ = new BehaviorSubject<Notice[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

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
      this._notices$.next(result.notices);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get notices$() { return this._notices$.asObservable(); }
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
    let notices = sort(NOTICES, sortColumn, sortDirection);

    // 2. filter
    notices = notices.filter(notice => matches(notice, searchTerm, this.pipe));
    const total = notices.length;

    // 3. paginate
    notices = notices.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({notices, total});
  }  


}
