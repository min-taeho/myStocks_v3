import { Component, OnInit } from '@angular/core';

interface PageList {
  path: string;
  name: string;
  css: string;
}


@Component({
  selector: 'app-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.css']
})
export class MainHeaderComponent implements OnInit {

  constructor() { }

  active = 'top';

  public isMenuCollapsed = false;

  pageList: PageList[] = [

    {
      path: '/main/portfolio',
      name: '포트폴리오',
      css: 'active'
    },
    {
      path: '/main/interestStock',
      name: '관심종목',
      css: 'active'
    },
    {
      path: '/main/tradingNote',
      name: '매매일지',
      css: 'active'
    },
    {
      path: '/main/notice',
      name: '게시판',
      css: 'active'
    }
  ]


  ngOnInit(): void {
  }

}
