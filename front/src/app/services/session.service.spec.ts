import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Observable } from 'rxjs';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  const mockSessionUser: SessionInformation = {
    username: 'testUser',
    token: 'abc123',
    admin: true,
    id: 1,
    firstName: 'test',
    lastName: 'user',
    type: 'user'
  };

  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with isLogged set to false', () => {
    expect(service.isLogged).toBe(false);
  });

  it('should initialize with sessionInformation set to undefined', () => {
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should return an Observable for $isLogged()', () => {
    expect(service.$isLogged()).toBeInstanceOf(Observable);
  });

  describe('logIn', () => {
    it('should set sessionInformation and isLogged to true on logIn', () => {
      service.logIn(mockSessionUser);
      expect(service.isLogged).toBe(true);
      expect(service.sessionInformation).toEqual(mockSessionUser);
    });
  });

  describe('logOut', () => {
    it('should set sessionInformation to undefined and isLogged to false on logOut', () => {
      service.logIn(mockSessionUser); // First log in
      service.logOut();
      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
    });
  });

  it('should emit the correct value via the $isLogged observable when logging in and out', done => {
    const expectedValues = [false, true, false];
    let index = 0;

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(expectedValues[index]);
      index++;
      if (index === expectedValues.length) {
        done();
      }
    });

    service.logIn(mockSessionUser);
    service.logOut();
  });

  it('should handle multiple logins and logouts', () => {
    service.logIn(mockSessionUser);
    expect(service.isLogged).toBe(true);

    service.logOut();
    expect(service.isLogged).toBe(false);

    service.logIn(mockSessionUser);
    expect(service.isLogged).toBe(true);

    service.logOut();
    expect(service.isLogged).toBe(false);
  });
});
