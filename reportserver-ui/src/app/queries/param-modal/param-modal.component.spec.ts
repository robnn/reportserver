import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamModalComponent } from './param-modal.component';

describe('ParamModalComponent', () => {
  let component: ParamModalComponent;
  let fixture: ComponentFixture<ParamModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
