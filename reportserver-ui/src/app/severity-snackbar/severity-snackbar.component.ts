import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-severity-snackbar',
  templateUrl: './severity-snackbar.component.html',
  styleUrls: ['./severity-snackbar.component.css']
})
export class SeveritySnackbarComponent implements OnInit {

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: any) { }

  ngOnInit() {
  }

}
