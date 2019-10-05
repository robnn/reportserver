import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalQueryEditorComponent } from './modal-query-editor.component';

describe('ModalQueryEditorComponent', () => {
  let component: ModalQueryEditorComponent;
  let fixture: ComponentFixture<ModalQueryEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalQueryEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalQueryEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
