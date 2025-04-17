import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { CoursesService } from '../services/courses.service';
import { catchError, Observable } from 'rxjs';
import { of } from 'rxjs';
import { MatDialog} from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { tap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-courses',
  standalone: false,
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss'
})

export class CoursesComponent implements OnInit {

  courses$: Observable<Course[]>;

  displayedColumns = ['_id', 'name', 'category', 'actions'];
  constructor(
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location

  ) {
    // In your component
    this.courses$ = this.coursesService.list()
    .pipe(
      tap(courses => console.log('Courses received:', courses)),
      catchError(error => {
        console.error('Error fetching courses:', error);
        this.onError('Error loading courses');
        return of([]);
      })
    );
  }


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

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }

}
