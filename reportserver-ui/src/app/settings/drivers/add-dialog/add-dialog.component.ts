import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { DriverService } from 'src/app/driver.service';
import { DbType } from 'src/app/model/driver';

@Component({
  selector: 'app-add-dialog',
  templateUrl: './add-dialog.component.html',
  styleUrls: ['./add-dialog.component.css']
})
export class AddDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<AddDialogComponent>,
    private driverService: DriverService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  @ViewChild('file') file;

  toUploads: FileList;


  ngOnInit() {
  }


  onFileAdded() {
    this.toUploads = this.file.nativeElement.files;
  }

  addFiles() {
    this.file.nativeElement.click();
  }

  uploadClick() {
    Array.from(this.toUploads).forEach(file => {
      this.driverService.createDriver(file, DbType.MY_SQL).subscribe(data => {
        this.dialogRef.close("SAVED");
      });
    });
    this.file.nativeElement.value = '';
  }
}
