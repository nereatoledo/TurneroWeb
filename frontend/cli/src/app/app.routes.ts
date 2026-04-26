import { Routes } from '@angular/router';
import { CentroAtencionDetailComponent } from './centro/centro-atencion-detail.component';
import { CentrosAtencionComponent } from './centro/centros-atencion.component';

export const routes: Routes = [
    {path: 'centros_atencion/new', component: CentroAtencionDetailComponent},
    {path: 'centros_atencion', component: CentrosAtencionComponent},
    {path: 'centros_atencion/:id', component: CentroAtencionDetailComponent} 
];