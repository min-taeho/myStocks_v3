import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CompanyInfoComponent } from '../popup/company-info/company-info.component';
import { Stock } from '../service/stock';
import { StockService } from '../service/stock.service';

@Component({
  selector: 'app-stock-info',
  templateUrl: './stock-info.component.html',
  styleUrls: ['./stock-info.component.css']
})
export class StockInfoComponent implements OnInit {

  @Input() name!: string;
  @Input() ticker!: string;
  editMode!: boolean;

  model: Stock = {
    id: 0,
    stockId: 0,
    ticker: '',
    stockName: '',
    businessCycle: '',
    sector: '',
    currentPrice: 0,
    highestPrice: 0,
    lowerPrice: 0,
    desiredPurchasePrice: 0,
    dividendYield: 0,
    fiveyearAvgDividendYield: 0,
    payoutRatio: 0,
    annualPayout: 0,
    dividendGrowth: 0,
    fiveyearGrowthRate: 0,
    companyInfo: ''   
  };

  constructor(public activeModal: NgbActiveModal,
              private modalService: NgbModal,
              private service: StockService) { }

  ngOnInit(): void {
    this.editMode = false;
    this.service.getStockDetail(this.ticker)
                .subscribe(item => {
                  console.log(item);
                  this.model = item;
                })

  }

  doChangeEditMode(flag: boolean) {
    this.editMode = flag;
  }

  doSave() {
    console.log(this.model);
    this.model.id = this.model.stockId;
    if(confirm("저장 하시겠습니까?")) {
      this.service.modify(this.model).subscribe({
        complete: () => {
          this.doChangeEditMode(false);
          setTimeout(() => {
            alert("저장 되었습니다.");
          }, 1000);         
        }        
      });
    }    
  }

  viewStockInfo() {
    const modalRef = this.modalService.open(CompanyInfoComponent, { size: 'xl', centered: true  } );
    modalRef.componentInstance.name = 'CompanyInfo';
    modalRef.componentInstance.companyInfo = this.model.companyInfo;
  }

  newWindow(type: string) {
    const url = (type === 'seekingalpha') ? "https://seekingalpha.com/symbol/"+this.ticker+"/dividends/scorecard"
                                          : "https://finance.yahoo.com/quote/"+this.ticker+"/key-statistics?p="+this.ticker;
    window.open(url, type);
  }

}
