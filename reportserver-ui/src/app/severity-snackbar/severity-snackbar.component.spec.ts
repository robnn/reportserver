import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeveritySnackbarComponent } from './severity-snackbar.component';

describe('SeveritySnackbarComponent', () => {
  let component: SeveritySnackbarComponent;
  let fixture: ComponentFixture<SeveritySnackbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeveritySnackbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeveritySnackbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
