import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { delay, first, tap, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private readonly API = 'http://localhost:8080/api/courses';

  constructor(private httpClient: HttpClient) {}

  list(): Observable<Course[]> {
    console.log("Chamando API:", this.API);

    // headers
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.httpClient.get<Course[]>(this.API, { headers })
    .pipe(
      first(),
      delay(5000),
      tap(courses => {
        console.log("Cursos recebidos:", courses);
      }),
      catchError(error => {
        console.error("Erro na API:", error);
        throw error;
      })
    );
  }

  save(record: Course){
    console.log(record)
    return this.httpClient.post<Course>(this.API, record).pipe(first());
  }

}
