import { Routes } from '@angular/router';
import { CentroAtencionDetailComponent } from './centro/centro-atencion-detail.component';
import { CentrosAtencionComponent } from './centro/centros-atencion.component';
import { HomeComponent } from './home/home.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { EspecialidadDetailComponent } from './especialidades/especialidades-detail.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    
    // --- Rutas de Centros de Atención ---
    {path: 'centros_atencion', component: CentrosAtencionComponent},
    {path: 'centros_atencion/new', component: CentroAtencionDetailComponent},
    {path: 'centros_atencion/:id', component: CentroAtencionDetailComponent}, 

    // --- Rutas de Especialidades ---
    {path: 'especialidades', component: EspecialidadesComponent},
    {path: 'especialidades/new', component: EspecialidadDetailComponent},
    {path: 'especialidades/:id', component: EspecialidadDetailComponent} 
];