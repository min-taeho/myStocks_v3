import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notice',
  templateUrl: './notice.component.html',
  styleUrls: ['./notice.component.css']
})
export class NoticeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  toAdd(): void {
    this.router.navigate([`main/notice/edit`]);
  }

}
