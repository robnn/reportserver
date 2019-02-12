import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatCardModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
