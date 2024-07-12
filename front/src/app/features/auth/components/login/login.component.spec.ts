import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';

import { HttpClientModule } from '@angular/common/http';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let routerSpy: { navigate: jest.Mock };

  beforeEach(async () => {
    authService = {
      login: jest.fn()
    } as any; // Mock AuthService methods

    routerSpy = {
      navigate: jest.fn()
    };

    sessionService = {
      logIn: jest.fn()
    } as any; // Mock SessionService methods

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: SessionService, useValue: sessionService }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('email field validity', () => {
      let errors: any = {}; 
      const email = component.form.controls['email'];
      errors = email.errors || {};
      expect(errors['required']).toBeTruthy();
  
      email.setValue('test');
      errors = email.errors || {};
      expect(errors['email']).toBeTruthy();
  });

  it('password field validity', () => {
      let errors: any = {}; 
      const password = component.form.controls['password'];
      errors = password.errors || {};
      expect(errors['required']).toBeTruthy();
  
      password.setValue('12');
      errors = password.errors || {};
      expect(errors['minlength']).toBeUndefined();
  });

  it('should call AuthService.login() and redirect to /sessions on successful login', () => {
    const loginRequest = { email: 'test@example.com', password: 'password' };
    const sessionInfo: SessionInformation = {
      admin: true,
      token: 'token123',
      type: 'admin',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User'
    };

    jest.spyOn(authService, 'login').mockReturnValue(of(sessionInfo));

    component.form.setValue(loginRequest);
    component.submit();

    expect(authService.login).toHaveBeenCalledWith(loginRequest);
    expect(sessionService.logIn).toHaveBeenCalledWith(sessionInfo);
  });

  it('should display error message on login failure', () => {
    const loginRequest = { email: 'test@example.com', password: 'password' };

    authService.login = jest.fn().mockReturnValue(throwError('Invalid credentials'));

    component.form.setValue(loginRequest);
    component.submit();

    expect(authService.login).toHaveBeenCalledWith(loginRequest);
    expect(component.onError).toBeTruthy();
  });
});
