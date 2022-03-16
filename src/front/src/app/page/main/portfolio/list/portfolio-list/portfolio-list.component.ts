import { DecimalPipe } from '@angular/common';
import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';
import { Portfolio } from './portfolio';
import { PortfolioListSortableHeader, SortEvent } from './portfolio-list.sortable.directive';
import { PortfolioService } from './portfolio.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StockInfoComponent } from 'src/app/page/common/stock/stock-info/stock-info.component';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.css'],
  providers: [PortfolioService, DecimalPipe]
})
export class PortfolioListComponent implements OnInit {

  portfolios$: Observable<Portfolio[]>;
  total$: Observable<number>;

  @ViewChildren(PortfolioListSortableHeader) headers!: QueryList<PortfolioListSortableHeader>;

  constructor(public service: PortfolioService,
              private modalService: NgbModal) {
    this.portfolios$ = service.portfolios$;
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
      const modalRef = this.modalService.open(StockInfoComponent, { size: 'xl', centered: true  } );
      modalRef.componentInstance.name = 'StockInfo';
  }

  ngOnInit(): void {

  }

}
