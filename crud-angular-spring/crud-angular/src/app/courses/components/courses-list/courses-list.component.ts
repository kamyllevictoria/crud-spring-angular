import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { Course } from '../../model/course';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table'; // Importe MatTableDataSource

@Component({
  selector: 'app-courses-list',
  standalone: false,
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss',
})
export class CoursesListComponent implements OnInit, OnChanges {

  // O Input qu recebe os cursos do componente pai
  @Input() courses: Course[] = [];

  @Output() add = new EventEmitter<any>();
  @Output() edit = new EventEmitter<any>();
  @Output() remove = new EventEmitter<any>();

  readonly displayedColumns: string[] = ['_id', 'name', 'category', 'actions'];

  dataSource = new MatTableDataSource<Course>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {

    this.dataSource.data = this.courses;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['courses'] && changes['courses'].currentValue) {
      this.dataSource.data = this.courses; 
      console.log('CoursesListComponent - Dados atualizados no dataSource:', this.dataSource.data);
    }
  }

  getCategoryIcon(category: string): string {
    const normalizedCategory = category.trim().toLowerCase().replace('-', '');

    if (normalizedCategory === "frontend") {
      return "code";
    } else if (normalizedCategory === "backend") {
      return "computer";
    } else if (normalizedCategory === "data") {
      return "school";
    } else {
      return "help";
    }
  }

  onAdd() {
    this.add.emit(true);
  }

  onEdit(course: Course) {
    this.edit.emit(course);
  }

  onRemove(course: Course){
    this.remove.emit(course);
  }
}
