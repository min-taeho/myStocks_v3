import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { SingleLayoutComponent } from './layout/single-layout/single-layout.component';
import { LoginComponent } from './page/auth/login/login.component';
import { SignUpComponent } from './page/auth/sign-up/sign-up.component';
import { NotFoundComponent } from './page/common/not-found/not-found.component';
import { InterestStockComponent } from './page/main/interest-stock/interest-stock.component';
import { NoticeListComponent } from './page/main/notice/list/notice-list/notice-list.component';
import { NoticeEditComponent } from './page/main/notice/mod/notice-edit/notice-edit.component';
import { NoticeComponent } from './page/main/notice/notice.component';
import { NoticeViewComponent } from './page/main/notice/view/notice-view/notice-view.component';
import { PortfolioComponent } from './page/main/portfolio/portfolio.component';
import { TradingNoteComponent } from './page/main/trading-note/trading-note.component';

const routes: Routes = [
  {
    path: 'auth',
    component: SingleLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signUp',
        component: SignUpComponent
      }
    ]
  },
  { 
    path: 'main',
    component: MainLayoutComponent,
    children: [
      {
        path: '',
        component: PortfolioComponent
      },
      {
        path: 'portfolio',
        component: PortfolioComponent
      },
      {
        path: 'interestStock',
        component: InterestStockComponent
      },
      {
        path: 'tradingNote',
        component: TradingNoteComponent
      },
      {
        path: 'notice',
        component: NoticeComponent,
        children: [
          {
            path: '',
            component: NoticeListComponent
          },
          {
            path: 'list',
            component: NoticeListComponent
          },
          {
            path: 'edit',
            component: NoticeEditComponent
          },
          {
            path: ':id',
            component: NoticeViewComponent
          },
          {
            path: 'edit/:id',
            component: NoticeEditComponent
          }
        ]
      }
    ]
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
