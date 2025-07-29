import { ConfirmationDialogComponent } from './../../components/courses-list/confirmation-dialog/confirmation-dialog.component';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { catchError, Observable } from 'rxjs';
import { of } from 'rxjs';
import { MatDialog} from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { tap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoursePage } from '../../model/course-page';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-courses',
  standalone: false,
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss'
})

export class CoursesComponent implements OnInit, AfterViewInit {

  courses$: Observable<CoursePage> | null = null;
  displayedColumns = ['name', 'category', 'actions'];

  // Use um setter para MatPaginator para garantir que ele esteja disponível
  // mesmo se estiver dentro de um *ngIf no HTML.
  private _paginator!: MatPaginator;
  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    if (mp) {
      this._paginator = mp;
      // Inicia a escuta por eventos de paginação assim que o paginador está disponível
      this._paginator.page.subscribe((event: PageEvent) => this.refresh(event));
    }
  }

  pageIndex = 0;
  pageSize = 10;
  totalElements = 0; // Propriedade para armazenar o total de elementos do backend

  constructor(
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private snackBar: MatSnackBar,
  ) {
    // A chamada inicial para refresh será feita em ngAfterViewInit,
    // garantindo que o paginador esteja pronto para receber os dados.
  }

  ngOnInit(): void {
    // ngOnInit é para inicialização de dados, não de elementos da view.
  }

  // ngAfterViewInit é chamado uma vez após a view do componente ser inicializada.
  ngAfterViewInit(): void {
    // Carrega a primeira página quando o componente é inicializado
    // e o paginador está disponível.
    this.refresh({ length: this.totalElements, pageIndex: this.pageIndex, pageSize: this.pageSize });
  }

  // O método refresh agora recebe o PageEvent do MatPaginator
  refresh(pageEvent: PageEvent){
    // Atualiza as propriedades do componente com os valores do evento do paginador
    this.pageIndex = pageEvent.pageIndex;
    this.pageSize = pageEvent.pageSize;

    // Chama o serviço com os novos parâmetros de paginação
    this.courses$ = this.coursesService.list(this.pageIndex, this.pageSize)
      .pipe(
        // O tap é executado quando a resposta da API é recebida
        tap(coursePage => {
          console.log('Courses received from API:', coursePage);
          // Atualiza o totalElements com base na resposta da API
          this.totalElements = coursePage.totalElements;
          // O MatPaginator já deve atualizar pageIndex e pageSize automaticamente
          // através dos bindings [pageIndex] e [pageSize] no HTML.
        }),
        catchError(error => {
          console.error('Error fetching courses:', error);
          this.onError('Error loading courses');
          // Retorna uma CoursePage vazia em caso de erro para evitar quebrar a UI
          // Certifique-se que 'course' ou 'courses' aqui corresponde ao seu backend
          return of({course: [], totalElements: 0, totalPages: 0} as CoursePage);
        })
      );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }

  onEdit(course: Course){
    this.router.navigate(['edit', course._id], {relativeTo: this.route});
  }

  onRemove(course: Course) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Are you sure to delete this course?',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.remove(course._id).subscribe(
          () => {
            // Ao remover, chame refresh com os parâmetros atuais para recarregar a página
            this.refresh({ length: this.totalElements, pageIndex: this.pageIndex, pageSize: this.pageSize });
            this.snackBar.open('Course removed successfully!', 'X', {
              duration: 4000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          (error) => {
            console.error('Error removing course:', error);
            this.onError('Error removing course.');
          }
        );
      }
    });
  }
}
