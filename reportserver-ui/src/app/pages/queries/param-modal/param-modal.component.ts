import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-param-modal',
  templateUrl: './param-modal.component.html',
  styleUrls: ['./param-modal.component.css']
})
export class ParamModalComponent implements OnInit {

  public paramValues: Array<any> = new Array();
  public paramKeys: Array<string>;

  constructor(public dialogRef: MatDialogRef<ParamModalComponent>,
    @Inject(MAT_DIALOG_DATA) public params: Map<string, string>) {
      this.paramKeys = Array.from(params.keys());
      this.paramValues = Array.from(params.values());
    }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  createParams(): void {
    const paramMap = this.createParamMap();
    this.dialogRef.close(paramMap);
  }

  createParamMap(): object {
    const map = {};
    this.paramKeys.forEach( (item, i) => {
      if (Number(this.paramValues[i])) {
        map[item] = Number(this.paramValues[i]);
      } else {
        map[item] = this.paramValues[i];
      }
    });
    return map;
  }
}
