import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { SingleLayoutComponent } from './layout/single-layout/single-layout.component';
import { LoginComponent } from './page/auth/login/login.component';
import { UserRegisterComponent } from './page/auth/user-register/user-register.component';
import { NotFoundComponent } from './page/common/not-found/not-found.component';
import { HeroComponent } from './page/main/hero/hero.component';
import { InterestStockComponent } from './page/main/interest-stock/interest-stock.component';
import { NoticeEditComponent } from './page/main/notice/notice-edit/notice-edit.component';
import { NoticeListComponent } from './page/main/notice/notice-list/notice-list.component';
import { NoticeViewComponent } from './page/main/notice/notice-view/notice-view.component';
import { NoticeComponent } from './page/main/notice/notice.component';
import { PortfolioComponent } from './page/main/portfolio/portfolio.component';
import { TradingNoteComponent } from './page/main/trading-note/trading-note.component';
import { RequireNoOAuthGuard } from './service/require-no-oauth.guard';
import { RequireOauthGuard } from './service/require-oauth.guard';

const routes: Routes = [
  {
    path: 'auth',
    component: SingleLayoutComponent,
    canActivateChild: [RequireNoOAuthGuard],
    children: [
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'signUp',
        component: UserRegisterComponent,        
      }
    ]
  },
  { 
    path: '',
    redirectTo: '/main',
    pathMatch: 'full'
  },  
  { 
    path: 'main',
    component: MainLayoutComponent,
    canActivateChild: [RequireOauthGuard],
    children: [
      {
        path: '',
        component: PortfolioComponent,
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
      },      
      {
        path: 'hero',
        component: HeroComponent
      },      
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
