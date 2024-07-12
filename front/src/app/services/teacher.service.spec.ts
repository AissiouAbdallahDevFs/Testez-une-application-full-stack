import { Teacher } from '../interfaces/teacher.interface';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TeacherService } from './teacher.service';

import { HttpClientModule } from '@angular/common/http';
import { expect } from '@jest/globals';
import { TestBed } from '@angular/core/testing';


describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;
  const apiUrl = 'api/teacher';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeDefined();
  });

  it('should retrieve all teachers', () => {
    const mockTeachers: Teacher[] = [
      { id: 1, lastName: 'John', firstName: 'Doe', createdAt: new Date(), updatedAt: new Date() }
    ];

    service.all().subscribe(teachers => {
      expect(teachers).toEqual(mockTeachers);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers);
  });

  it('should retrieve a teacher by ID', () => {
    const teacherId = '1';
    const mockTeacher: Teacher = { id: parseInt(teacherId), lastName: 'John', firstName: 'Doe', createdAt: new Date(), updatedAt: new Date() };

    service.detail(teacherId).subscribe(teacher => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpMock.expectOne(`${apiUrl}/${teacherId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher);
  });

  it('should handle teacher not found', () => {
    const teacherId = '10';

    service.detail(teacherId).subscribe(
      teacher => fail('expected an error, but received a teacher'),
      error => expect(error).toBeTruthy()
    );

    const req = httpMock.expectOne(`${apiUrl}/${teacherId}`);
    expect(req.request.method).toBe('GET');
    req.flush('Teacher not found', { status: 404, statusText: 'Not Found' });
  });
});
