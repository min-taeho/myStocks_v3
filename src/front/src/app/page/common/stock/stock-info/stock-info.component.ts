import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-stock-info',
  templateUrl: './stock-info.component.html',
  styleUrls: ['./stock-info.component.css']
})
export class StockInfoComponent implements OnInit {

  @Input() name!: string;
  editMode!: boolean;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.editMode = false;
  }

  doChangeEditMode() {
    this.editMode = true;
  }

  doSave() {
    
  }

}
