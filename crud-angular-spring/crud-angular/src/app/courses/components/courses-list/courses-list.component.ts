import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Course } from '../../model/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  standalone: false,

  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss'
})
export class CoursesListComponent implements OnInit{

  @Input()courses: Course[] = [];
  @Output() add = new EventEmitter<any>();

  readonly displayedColumns = ['_id', 'name', 'category', 'actions'];

  constructor(

        private router: Router,
        private route: ActivatedRoute,
  ){}

  ngOnInit(): void {

  }
  getCategoryIcon(category: string): string {
    if (category === "Frontend") {
      return "code"
    } else if (category === "Backend") {
      return "computer"
      return "data_usage"
    } else {
      return "school"
    }
  }
  onAdd(){
    this.add.emit(true);
  }
}
