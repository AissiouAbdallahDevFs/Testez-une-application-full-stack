import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MeComponent } from './me.component';
import { UserService } from '../../services/user.service';
import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { SessionInformation } from '../../interfaces/sessionInformation.interface';
import { expect } from '@jest/globals';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockRouter: Router;
  let userService: UserService;
  let sessionService: SessionService;
  let mockMatSnackBar: MatSnackBar;

  const mockUser: User = {
    id: 1,
    email: 'john.doe@example.com',
    firstName: 'John',
    lastName: 'Doe',
    password: 'password',
    admin: true,
    createdAt: new Date(),
    updatedAt: new Date()
  };

  const mockSessionInformation: SessionInformation = {
    id: 1,
    token: 'token',
    type: 'type',
    username: 'john.doe',
    firstName: 'John',
    lastName: 'Doe',
    admin: true
  };

  beforeEach(async () => {
    mockRouter = {
      navigate: jest.fn()
    } as unknown as Router;

    userService = {
      getById: jest.fn().mockReturnValue(of(mockUser)),
      delete: jest.fn().mockReturnValue(of({}))
    } as unknown as UserService;

    sessionService = {
      sessionInformation: mockSessionInformation,
      logOut: jest.fn()
    } as unknown as SessionService;

    mockMatSnackBar = {
      open: jest.fn()
    } as unknown as MatSnackBar;

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        MatCardModule,
        MatIconModule
      ],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: UserService, useValue: userService },
        { provide: SessionService, useValue: sessionService },
        { provide: MatSnackBar, useValue: mockMatSnackBar }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call UserService.getById and set user', () => {
      component.ngOnInit();
      expect(userService.getById).toHaveBeenCalledWith(mockSessionInformation.id.toString());
      expect(component.user).toEqual(mockUser);
    });
  });

  describe('back', () => {
    it('should call window.history.back()', () => {
      const backSpy = jest.spyOn(window.history, 'back');
      component.back();
      expect(backSpy).toHaveBeenCalledTimes(1);
    });
  });

  describe('delete', () => {
    it('should call userService.delete()', () => {
      const deleteSpy = jest.spyOn(userService, 'delete').mockReturnValue(of({}));
      component.delete();
      expect(deleteSpy).toHaveBeenCalledWith(mockSessionInformation.id.toString());
    });

    it('should call sessionService.logOut()', () => {
      component.delete();
      expect(sessionService.logOut).toHaveBeenCalled();
    });

    it('should call matSnackBar.open() with correct parameters', () => {
      component.delete();
      expect(mockMatSnackBar.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    });

    it('should navigate to the home page', () => {
      const navigateSpy = jest.spyOn(mockRouter, 'navigate');
      component.delete();
      expect(navigateSpy).toHaveBeenCalledWith(['/']);
    });
  });
});
