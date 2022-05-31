import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { Hero } from '../hero/service/hero';
import { HeroListService } from '../hero/service/hero-list.service';
import { PortfolioService } from '../portfolio/service/portfolio.service';
import { PortfolioStock } from '../portfolio/service/portfolioStock';

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

  active = 'top';
  isMenuCollapsed = false;
  isLoggedIn = false;
  isContinue = false;

  constructor(private token: TokenStorageService,
              private service: PortfolioService,
              private route: ActivatedRoute,
              private router: Router) { }

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
    },
    /*
    {
      path: '/main/hero',
      name: '히어로',
      css: 'active'
    },
    */    
  ]

  ngOnInit(): void {
    this.isLoggedIn = (this.token.getAccessToken()) ? true : false;
  }

  doLogout(): void {
    if (confirm('로그아웃 하시겠습니까?')) {
      this.token.signOut();
      window.location.href = '/';
    }
  }  

  applyCurrentInfo(): void {
    if(confirm("정보 현행화 진행하시겠습니까?")) {
      this.isContinue = true;
      const params = { email: this.token.getUser().email };
      this.service.applyCurrentInfo(params).subscribe({
        complete: () => {
          this.isContinue = false;
          alert('주식정보가 현행화 되었습니다.');

          // page refresh
          let currentUrl = this.router.url;
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
            this.router.navigate([currentUrl]);
          });
        }
      })
    }


   
  }


}
