import { NgModule } from '@angular/core';
import {
  MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule,
  MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatSnackBarModule,
  MatTabsModule, MatExpansionModule, MatDialogModule, MatPaginatorModule, MatTableModule,
  MatCheckboxModule,
  MatProgressSpinnerModule, MatNativeDateModule
} from '@angular/material';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatGridListModule} from '@angular/material/grid-list';

@NgModule({
  declarations: [],
  imports: [
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatSnackBarModule,
    MatCardModule,
    MatTabsModule,
    MatExpansionModule,
    MatDialogModule,
    MatPaginatorModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  exports: [
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatSnackBarModule,
    MatCardModule,
    MatTabsModule,
    MatExpansionModule,
    MatDialogModule,
    MatPaginatorModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class MaterialModule { }
