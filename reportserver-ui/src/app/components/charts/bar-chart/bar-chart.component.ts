import { Component, OnInit, Input } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import * as pluginDataLabels from 'chart.js';
import { Label } from 'ng2-charts';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  @Input() barChartDataSet: number[];
  @Input() barChartLabels: Label[];

  public barChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      labels: {
        fontColor: '#fff'
      }
    },
    scales: {
      xAxes: [{ gridLines: { color: 'white' }, ticks: { fontColor: 'white' } }],
      yAxes: [{ gridLines: { color: 'white' }, ticks: { fontColor: 'white' } }]
    },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };
  public barChartType: ChartType = 'bar';
  public barChartLegend = false;
  public barChartPlugins = [pluginDataLabels];
  public barChartData: ChartDataSets[];

  constructor() { }

  ngOnInit() {
    this.barChartData = [
      {
        data: this.barChartDataSet,
        backgroundColor: 'rgb(0,150,136)',
        hoverBackgroundColor: 'rgb(255,255,255)'
      }
    ];
  }

}
