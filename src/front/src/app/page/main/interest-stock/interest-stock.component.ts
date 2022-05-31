import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InterestStockListComponent } from './interest-stock-list/interest-stock-list.component';
import { AddTickerInterestStockComponent } from './popup/add-ticker/add-ticker-interest-stock.component';
import { InterestStockService } from './service/interest-stock.service';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-interest-stock',
  templateUrl: './interest-stock.component.html',
  styleUrls: ['./interest-stock.component.css']
})
export class InterestStockComponent implements OnInit {

  @ViewChild(InterestStockListComponent) list!:InterestStockListComponent;

  constructor(private service: InterestStockService,
    private modalService: NgbModal) {
  }

  ngOnInit(): void {
  }

  popupAddTicker(e: any) {
    const modalRef = this.modalService.open(AddTickerInterestStockComponent, { centered: true  } );
    modalRef.componentInstance.name = 'AddTicker';
    modalRef.result.then((result => {
      this.list.getList();
    }))    
  }  

  issueExcel(): void {
    let fileName= 'interest_stock_list.xlsx';
    let element = document.getElementById('interest_stock_list');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
 
    /* save to file */  
    XLSX.writeFile(wb, fileName);

  }


}
