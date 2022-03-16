import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-notice-edit',
  templateUrl: './notice-edit.component.html',
  styleUrls: ['./notice-edit.component.css']
})
export class NoticeEditComponent implements OnInit {

  id!: string;
  mode!: string;

  constructor(private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    //console.log('notice-view', this.id, this.route.snapshot.paramMap.get('id'));
    this.mode = this.id ? 'modify' : 'register'
    console.log(`id=${this.id}, mode=${this.mode}`);
  }

  toList(): void {
    this.router.navigate(["main/notice/list"]);
  }  
  save(): void {

  }
}
