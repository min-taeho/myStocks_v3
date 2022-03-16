import { Component } from '@angular/core';

interface PageList {
  path: string;
  name: string;
  css: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';

  pageList: PageList[] = [
    {
      path: '/auth/login',
      name: 'Login',
      css: 'active'
    },
    {
      path: '/auth/signUp',
      name: 'Sign Up',
      css: 'active'
    },
    {
      path: '/main',
      name: '메인',
      css: 'active'
    },
  ]

}
