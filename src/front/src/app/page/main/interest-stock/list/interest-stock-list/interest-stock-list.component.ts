import { DecimalPipe } from '@angular/common';
import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { InterestStock } from './interest-stock';
import { InterestStockService } from './interest-stock.service';
import { InterestStockListSortableHeader, InterestStockSortEvent } from './interestStock-list.sortable.directive';
import { StockInfoComponent } from 'src/app/page/common/stock/stock-info/stock-info.component';

@Component({
  selector: 'app-interest-stock-list',
  templateUrl: './interest-stock-list.component.html',
  styleUrls: ['./interest-stock-list.component.css'],
  providers: [InterestStockService, DecimalPipe]
})
export class InterestStockListComponent implements OnInit {

  interestStocks$: Observable<InterestStock[]>;
  total$: Observable<number>;

  @ViewChildren(InterestStockListSortableHeader) headers!: QueryList<InterestStockListSortableHeader>;

  constructor(public service: InterestStockService,
              private modalService: NgbModal) {
    this.interestStocks$ = service.interestStocks$;
    this.total$ = service.total$;
  }

  onSort({column, direction}: InterestStockSortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    this.service.sortColumn = column;
    this.service.sortDirection = direction;
  }

  rowDoubleClick(e: any, value: string) {
    const modalRef = this.modalService.open(StockInfoComponent, { size: 'xl', centered: true  } );
    modalRef.componentInstance.name = 'StockInfo';
}


  ngOnInit(): void {

  }
}
