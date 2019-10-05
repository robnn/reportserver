import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DriverService } from 'src/app/service/driver.service';
import { DbType } from 'src/app/model/driver';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-add-dialog',
  templateUrl: './add-dialog.component.html',
  styleUrls: ['./add-dialog.component.css']
})
export class AddDialogComponent {

  public types = Object.keys(DbType);
  public selectedDbType: DbType = DbType.POSTGRESQL;

  constructor(
    public dialogRef: MatDialogRef<AddDialogComponent>,
    private driverService: DriverService,
    private notificationService: NotificationService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  @ViewChild('file') file;

  toUpload: FileList;


  ngOnInit() {
  }


  onFileAdded() {
    this.toUpload = this.file.nativeElement.files;
  }

  addFiles() {
    this.file.nativeElement.click();
  }

  uploadClick() {

    this.driverService.createDriver(this.toUpload[0], this.selectedDbType).subscribe(data => {
      this.dialogRef.close("SAVED");
    }, error => {
      this.notificationService.addNotification(error.error);
    });

    this.file.nativeElement.value = '';
  }
}
