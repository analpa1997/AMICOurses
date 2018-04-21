

import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Component({selector: 'ngbd-carousel-basic', templateUrl: './carousel-basic.html', styles: [`
    /deep/ .imgCourse{
      margin: auto;
      width: 20vmax;
      height: 20vmax;
    }

    /deep/ .carousel-inner > .carousel-item > img {
      margin: auto;
      width: 15vmax;
      height: 15vmax;
    }

    /deep/ .carousel {
      height: 18vmax;
    }

    /deep/ .carousel-indicators .active {
      background-color: red!important;
    }

    /deep/  .carousel-indicators > li {
      background-color: black!important;
    }

    /deep/ .carousel-control-next {
      background-color: black;
    }

    /deep/  .carousel-control-prev {
      background-color: black
    };

    /deep/  .carousel-control-next > .sr-only {
      color: black;
    }

  `]
})
export class NgbdCarouselBasic implements OnInit {
  images: Array<string>;

  constructor(private _http: HttpClient) {}

  ngOnInit() {
    this.images = new Array();
    this.images.push('../../../assets/img/05.png');
    this.images.push('../../../assets/img/urjc.png');
    this.images.push('../../../assets/img/06.png');
}}

