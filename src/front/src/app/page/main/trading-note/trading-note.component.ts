import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { TradingNote } from './service/trading-note';
import { TradingNoteRegisterComponent } from './trading-note-register/trading-note-register.component';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-trading-note',
  templateUrl: './trading-note.component.html',
  styleUrls: ['./trading-note.component.css']
})
export class TradingNoteComponent implements OnInit {

  @ViewChild(TradingNoteRegisterComponent) regComp!: TradingNoteRegisterComponent;

  constructor() { }

  ngOnInit(): void {
  }

  /**
   * 매매일지 수정을 위해 form에 데이터 세팅
   * TradingNoteList 컴포넌트에서 row 클릭 이벤트에 의해 실행
   * @param item 
   */
  setDetail(item: TradingNote): void {
    this.regComp.setDetail(item);
  }

  issueExcel(): void {
    let fileName= 'trading_note.xlsx';
    let element = document.getElementById('trading_note');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
 
    /* save to file */  
    XLSX.writeFile(wb, fileName);

  }

}
