import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LoginComponent } from './page/auth/login/login.component';
import { SignUpComponent } from './page/auth/sign-up/sign-up.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { SingleLayoutComponent } from './layout/single-layout/single-layout.component';
import { PortfolioComponent } from './page/main/portfolio/portfolio.component';
import { MainHeaderComponent } from './page/main/main-header/main-header.component';
import { NotFoundComponent } from './page/common/not-found/not-found.component';
import { InterestStockComponent } from './page/main/interest-stock/interest-stock.component';
import { TradingNoteComponent } from './page/main/trading-note/trading-note.component';
import { NoticeComponent } from './page/main/notice/notice.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PortfolioListSortableHeader } from './page/main/portfolio/list/portfolio-list/portfolio-list.sortable.directive';
import { PortfolioListComponent } from './page/main/portfolio/list/portfolio-list/portfolio-list.component';
import { InterestStockListComponent } from './page/main/interest-stock/list/interest-stock-list/interest-stock-list.component';
import { InterestStockListSortableHeader } from './page/main/interest-stock/list/interest-stock-list/interestStock-list.sortable.directive';
import { TradingNoteListComponent } from './page/main/trading-note/list/trading-note-list/trading-note-list.component';
import { TradingNoteRegisterComponent } from './page/main/trading-note/reg/trading-note-register/trading-note-register.component';
import { NoticeListComponent } from './page/main/notice/list/notice-list/notice-list.component';
import { NoticeEditComponent } from './page/main/notice/mod/notice-edit/notice-edit.component';
import { NoticeViewComponent } from './page/main/notice/view/notice-view/notice-view.component';
import { NoticeListSortableHeader } from './page/main/notice/list/notice-list/notice-list.sortable.directive';
import { StockInfoComponent } from './page/common/stock/stock-info/stock-info.component';
import { FairPriceFigureComponent } from './page/common/stock/fair-price-figure/fair-price-figure.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    MainLayoutComponent,
    SingleLayoutComponent,
    PortfolioComponent,
    MainHeaderComponent,
    NotFoundComponent,
    InterestStockComponent,
    TradingNoteComponent,
    NoticeComponent,
    PortfolioListSortableHeader,
    PortfolioListComponent,
    InterestStockListComponent,
    InterestStockListSortableHeader,
    TradingNoteListComponent,
    TradingNoteRegisterComponent,
    NoticeListComponent,
    NoticeEditComponent,
    NoticeViewComponent,
    NoticeListSortableHeader,
    StockInfoComponent,
    FairPriceFigureComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
