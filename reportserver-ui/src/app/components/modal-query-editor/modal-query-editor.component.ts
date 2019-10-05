import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { PagedQueryResponse } from 'src/app/model/pagedQueryResponse';

@Component({
  selector: 'app-modal-query-editor',
  templateUrl: './modal-query-editor.component.html',
  styleUrls: ['./modal-query-editor.component.css']
})
export class ModalQueryEditorComponent implements OnInit {

  public queryRequest: PagedQueryRequest;

  constructor(public dialogRef: MatDialogRef<ModalQueryEditorComponent>,
    @Inject(MAT_DIALOG_DATA) public query: PagedQueryRequest) { 
      this.queryRequest = query;
    }

  ngOnInit() {
  }

  onSaveAction(event: Event) {   
    this.dialogRef.close('SAVED');
  }
  
}
