import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { delay, first, tap, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private readonly API = 'http://localhost:8080/api/courses';

  constructor(private httpClient: HttpClient) {}

  list(page = 0, pageSize = 10): Observable<CoursePage> {
    console.log("Chamando API:", this.API);

    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', pageSize.toString());

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.httpClient.get<CoursePage>(this.API, { params: params, headers: headers })
    .pipe(
      first(),
      tap(coursePage => {
        console.log("Página de Cursos recebida:", coursePage);
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
