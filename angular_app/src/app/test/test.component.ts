import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../core/project.service';

@Component({
    selector: 'app-test',
    templateUrl: './test.component.html',
    styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

    message: string;

    constructor(private _projectService: ProjectService) {
    }

    ngOnInit() {
        this._projectService.getMessage().subscribe(message => {
            this.message= JSON.stringify(message);
        });
    }

}
