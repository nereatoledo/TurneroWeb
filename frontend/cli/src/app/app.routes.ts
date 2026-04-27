import { Routes } from '@angular/router';
import { CentroAtencionDetailComponent } from './centro/centro-atencion-detail.component';
import { CentrosAtencionComponent } from './centro/centros-atencion.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'centros_atencion/new', component: CentroAtencionDetailComponent},
    {path: 'centros_atencion', component: CentrosAtencionComponent},
    {path: 'centros_atencion/:id', component: CentroAtencionDetailComponent} 
];