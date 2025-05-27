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
      //delay(5000),
      tap(courses => {
        console.log("Cursos recebidos:", courses);
      }),
      catchError(error => {
        console.error("Erro na API:", error);
        throw error;
      })
    );
  }

  loadById(id: string){
    return this.httpClient.get<Course>(`${this.API}/${id}`)
  }

  save(record: Partial<Course>){
    console.log(record)
    if(record._id){
      console.log('update');
      return this.update(record);
    }
    console.log('create')
    return this.create(record);
  }

  private create(record: Partial<Course>){
    return this.httpClient.post<Course>(this.API, record).pipe(first());
  }

  private update(record: Partial<Course>){
    return this.httpClient.put<Course>(`${this.API}/${record._id}`, record).pipe(first());
  }

  remove(id: string){
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());

  }

}
