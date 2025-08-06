import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { of } from 'rxjs';

import { CourseResolver } from './course.resolver';
import { CoursesService } from '../services/courses.service';
import { Course } from '../model/course';

describe('CourseResolver', () => {
  let resolver: CourseResolver;
  let coursesServiceSpy: jasmine.SpyObj<CoursesService>;

  beforeEach(() => {
    coursesServiceSpy = jasmine.createSpyObj('CoursesService', ['loadById']);

    TestBed.configureTestingModule({
      providers: [
        CourseResolver,
        { provide: CoursesService, useValue: coursesServiceSpy }
      ]
    });

    resolver = TestBed.inject(CourseResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });

  it('should resolve a course when a valid ID is provided', (done) => {
    const mockCourse: Course = { _id: '1', name: 'Angular', category: 'Front-end', lessons: [] };
    const route = { params: { id: '1' } } as unknown as ActivatedRouteSnapshot;
    const state = {} as unknown as RouterStateSnapshot;

    coursesServiceSpy.loadById.and.returnValue(of(mockCourse));

    resolver.resolve(route, state).subscribe(course => {
      expect(coursesServiceSpy.loadById).toHaveBeenCalledWith('1');
      expect(course).toEqual(mockCourse);
      done();
    });
  });

  it('should return a new course object when no ID is provided', (done) => {
    const newCourse = {_id: '', name: '', category: '', lessons: []};
    const route = { params: {} } as unknown as ActivatedRouteSnapshot;
    const state = {} as unknown as RouterStateSnapshot;

    resolver.resolve(route, state).subscribe(course => {
      expect(coursesServiceSpy.loadById).not.toHaveBeenCalled();
      expect(course).toEqual(newCourse);
      done();
    });
  });
});
