import { DecimalPipe } from '@angular/common';
import { Component, OnInit, QueryList, ViewChildren, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Notice } from './notice';
import { NoticeListSortableHeader, SortEvent } from './notice-list.sortable.directive';
import { NoticeService } from './notice.service';


@Component({
  selector: 'app-notice-list',
  templateUrl: './notice-list.component.html',
  styleUrls: ['./notice-list.component.css'],
  providers: [NoticeService, DecimalPipe]
})
export class NoticeListComponent implements OnInit {

  notices$: Observable<Notice[]>;
  total$: Observable<number>;

  @ViewChildren(NoticeListSortableHeader) headers!: QueryList<NoticeListSortableHeader>;

  constructor(public service: NoticeService,
              private router: Router) {
    this.notices$ = service.notices$;
    this.total$ = service.total$;
  }

  onSort({column, direction}: SortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    this.service.sortColumn = column;
    this.service.sortDirection = direction;
  }

  rowDoubleClick(e: any, value: number) {
    this.router.navigate([`main/notice/${value}`]);
  }
  toRigister(): void {
    this.router.navigate(["main/notice/edit/"]);
  }  

  ngOnInit(): void {
  }

}
