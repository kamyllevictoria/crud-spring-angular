import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CourseFormComponent } from './course-form.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { MatToolbarModule  } from '@angular/material/toolbar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatOption } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

describe('CourseFormComponent', () => {
  let component: CourseFormComponent;
  let fixture: ComponentFixture<CourseFormComponent>;
  const mockCourse = { _id: '1', name: 'Angular', category: 'front-end', lessons: [] };

  const mockActivatedRoute = {
    snapshot: {
      data: {
        course: mockCourse
      }
    },
    paramMap: of(new Map([['id', '1']]))
  }
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CourseFormComponent],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatToolbarModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
        MatIconModule
      ],
      providers: [{
        provide: ActivatedRoute, useValue: mockActivatedRoute
      }]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
