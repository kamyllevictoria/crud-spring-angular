import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { Course } from '../../model/course';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource, MatTable } from '@angular/material/table'; // Importe MatTable

@Component({
  selector: 'app-courses-list',
  standalone: false,
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss',
})
export class CoursesListComponent implements OnInit, OnChanges {

  @Input() courses: Course[] = []; // O Input que recebe os cursos do componente pai
  @Output() add = new EventEmitter<any>();
  @Output() edit = new EventEmitter<any>();
  @Output() remove = new EventEmitter<any>();

  readonly displayedColumns: string[] = ['_id', 'name', 'category', 'actions'];

  // Não inicialize dataSource aqui, ele será inicializado no ngOnChanges
  dataSource = new MatTableDataSource<Course>();

  // Adicione uma referência à MatTable para forçar a renderização
  @ViewChild(MatTable) table!: MatTable<Course>;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    // No ngOnInit, inicialize o dataSource apenas se houver dados iniciais
    // A principal lógica de atualização estará no ngOnChanges
    if (this.courses && this.courses.length > 0) {
      this.dataSource = new MatTableDataSource<Course>(this.courses);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // Verifica se a propriedade 'courses' mudou e se tem um valor
    if (changes['courses'] && changes['courses'].currentValue) {
      // **MUDANÇA CHAVE AQUI:** Crie uma NOVA instância de MatTableDataSource
      // Isso força a MatTable a reavaliar completamente sua fonte de dados.
      this.dataSource = new MatTableDataSource<Course>(this.courses);

      // Opcional: Chame renderRows() como um último recurso, embora criar nova instância
      // de DataSource geralmente já resolva.
      if (this.table) {
        this.table.renderRows();
        console.log('CoursesListComponent - renderRows() chamado para atualizar a tabela.');
      }

      console.log('CoursesListComponent - Nova instância de dataSource criada e dados atualizados:', this.dataSource.data);
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
