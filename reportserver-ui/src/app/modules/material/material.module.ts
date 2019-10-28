import { NgModule } from '@angular/core';
import {
  MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule,
  MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatSnackBarModule,
  MatTabsModule, MatExpansionModule, MatDialogModule, MatPaginatorModule, MatTableModule,
  MatCheckboxModule,
  MatProgressSpinnerModule
} from '@angular/material';
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
    MatGridListModule
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
    MatGridListModule
  ]
})
export class MaterialModule { }
