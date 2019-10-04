import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material/snack-bar';
import { Message } from '../../model/message';
import { Severity } from '../../model/severity';

@Component({
  selector: 'app-severity-snackbar',
  templateUrl: './severity-snackbar.component.html',
  styleUrls: ['./severity-snackbar.component.css']
})
export class SeveritySnackbarComponent implements OnInit {

  snackBarRef: MatSnackBarRef<SeveritySnackbarComponent>;

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: Message) { }

  ngOnInit() {
  }

  dismiss() {
    this.snackBarRef.dismiss();
  }

  getIconName(severity: Severity) : string {
    switch(severity){
      case Severity.WARNING:
        return "warning";
      case Severity.ERROR:
        return "error";
      case Severity.INFO:
        return "info";
    }
  }

  getIconColor(severity: Severity) : string {
    switch(severity){
      case Severity.WARNING:
        return "warn";
      case Severity.ERROR:
        return "warn";
      case Severity.INFO:
        return "accent";
    }
  }

}
