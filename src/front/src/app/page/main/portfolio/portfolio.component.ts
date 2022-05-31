import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Hero } from '../hero/service/hero';
import { AddPortfolioComponent } from './popup/add-portfolio/add-portfolio.component';
import { AddTickerComponent } from './popup/add-ticker/add-ticker.component';
import { Portfolio } from './service/portfolio';
import { PortfolioService } from './service/portfolio.service';
import { PortfolioStock } from './service/portfolioStock';
import * as XLSX from 'xlsx';
import { PortfolioListService } from './service/portfolio-list.service';
import { PortfolioListComponent } from './portfolio-list/portfolio-list.component';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})
export class PortfolioComponent implements OnInit {

  portfolios!: Portfolio[];
  selectedPortfolioId!: number;

  @ViewChild(PortfolioListComponent) list!:PortfolioListComponent;

  constructor(private service: PortfolioService,
              private listService: PortfolioListService,
              private modalService: NgbModal) {
    this.getPortfolioList();                
  }

  ngOnInit(): void {
    this.service.selectedId.subscribe(id => this.selectedPortfolioId = id);
  }

  popupAddPortfolio(e: any) {
    const modalRef = this.modalService.open(AddPortfolioComponent, { centered: true  } );
    modalRef.componentInstance.name = 'AddPortfolio';
    modalRef.result.then((result => {
      this.getPortfolioList();
    }))

  }

  changePortfolio(e: any) {
    this.service.setSelectedId(e.target.value);
    this.list.getList();
  }

  popupAddTicker(e: any) {
    const modalRef = this.modalService.open(AddTickerComponent, { centered: true  } );
    modalRef.componentInstance.name = 'AddTicker';
    modalRef.componentInstance.portfolioId = this.selectedPortfolioId;
    modalRef.result.then((result => {
      //this.list.getList();
    }))    
  }  
  
  getPortfolioList() {
    this.service.getPortfolioList().subscribe({
      next: (data: Portfolio[]) => {
        this.portfolios = data;
      },
      error: error => console.log(error),
      complete: () => {
        if (!this.selectedPortfolioId) this.service.setSelectedId(this.portfolios[0].portfolioId);
      }    
    });    
  }

  issueExcel(): void {
    let fileName= 'portfolio_list.xlsx';
    let element = document.getElementById('portfolio_list');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
 
    /* save to file */  
    XLSX.writeFile(wb, fileName);

  }

}
