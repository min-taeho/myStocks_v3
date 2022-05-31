import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { SingleLayoutComponent } from './layout/single-layout/single-layout.component';
import { LoginComponent } from './page/auth/login/login.component';
import { UserRegisterComponent } from './page/auth/user-register/user-register.component';
import { NotFoundComponent } from './page/common/not-found/not-found.component';
import { StockInfoComponent } from './page/common/stock/stock-info/stock-info.component';
import { FairPriceFigureComponent } from './page/common/stock/fair-price-figure/fair-price-figure.component';
import { PortfolioComponent } from './page/main/portfolio/portfolio.component';
import { PortfolioListComponent } from './page/main/portfolio/portfolio-list/portfolio-list.component';
import { AddPortfolioComponent } from './page/main/portfolio/popup/add-portfolio/add-portfolio.component';
import { AddTickerComponent } from './page/main/portfolio/popup/add-ticker/add-ticker.component';
import { ModifyStockComponent } from './page/main/portfolio/popup/modify-stock/modify-stock.component';
import { InterestStockComponent } from './page/main/interest-stock/interest-stock.component';
import { InterestStockListComponent } from './page/main/interest-stock/interest-stock-list/interest-stock-list.component';
import { MainHeaderComponent } from './page/main/main-header/main-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HeroComponent } from './page/main/hero/hero.component';
import { HeroListComponent } from './page/main/hero/hero-list/hero-list.component';
import { CommonModule, DecimalPipe } from '@angular/common';
import { NgxBootstrapIconsModule } from 'ngx-bootstrap-icons';
import { trash3, clipboard2Check, plus, check, eraser, xSquare } from 'ngx-bootstrap-icons';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PortfolioListSortableHeader } from './page/main/portfolio/portfolio-list/portfolio-list.sortable.directive';
import { authInterceptorProviders } from './interceptor/auth.interceptor';
import { InterestStockListSortableHeader } from './page/main/interest-stock/interest-stock-list/interest-stock-list.sortable.directive';
import { AddTickerInterestStockComponent } from './page/main/interest-stock/popup/add-ticker/add-ticker-interest-stock.component';
import { TradingNoteComponent } from './page/main/trading-note/trading-note.component';
import { TradingNoteListComponent } from './page/main/trading-note/trading-note-list/trading-note-list.component';
import { TradingNoteRegisterComponent } from './page/main/trading-note/trading-note-register/trading-note-register.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { NoticeComponent } from './page/main/notice/notice.component';
import { NoticeListComponent } from './page/main/notice/notice-list/notice-list.component';
import { NoticeEditComponent } from './page/main/notice/notice-edit/notice-edit.component';
import { NoticeViewComponent } from './page/main/notice/notice-view/notice-view.component';
import { NoticeListSortableHeader } from './page/main/notice/notice-list/notice-list.sortable.directive';
import { CompanyInfoComponent } from './page/common/stock/popup/company-info/company-info.component';

// Select some icons (use an object, not an array)
const icons = {
  trash3,
  clipboard2Check,
  plus, check, eraser, xSquare
};

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    SingleLayoutComponent,
    LoginComponent,
    UserRegisterComponent,
    NotFoundComponent,
    StockInfoComponent,
    FairPriceFigureComponent,
    PortfolioComponent,
    PortfolioListComponent,
    PortfolioListSortableHeader,
    AddPortfolioComponent,
    AddTickerComponent,
    ModifyStockComponent,
    InterestStockComponent,
    InterestStockListComponent,
    InterestStockListSortableHeader,
    AddTickerInterestStockComponent,
    MainHeaderComponent,
    HeroComponent,
    HeroListComponent,
    TradingNoteComponent,
    TradingNoteListComponent,
    TradingNoteRegisterComponent,
    NoticeComponent,
    NoticeListComponent,
    NoticeEditComponent,
    NoticeViewComponent,
    NoticeListSortableHeader,
    CompanyInfoComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    NgxBootstrapIconsModule.pick(icons),
    BrowserAnimationsModule,
    MatProgressBarModule
  ],
  providers: [
    authInterceptorProviders,
    DecimalPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
