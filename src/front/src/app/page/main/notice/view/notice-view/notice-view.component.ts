import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-notice-view',
  templateUrl: './notice-view.component.html',
  styleUrls: ['./notice-view.component.css']
})
export class NoticeViewComponent implements OnInit {

  id!: string;

  constructor(private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    //console.log('notice-view', this.id, this.route.snapshot.paramMap.get('id'));

  }

  toList(): void {
    this.router.navigate(["main/notice/list"]);
  }
  toModify(): void {
    this.router.navigate([`main/notice/edit/${this.id}`]);
  }
  delete(): void {
    
  }

}
