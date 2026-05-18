import { Routes } from '@angular/router';
import { CentroAtencionDetailComponent } from './centro/centro-atencion-detail.component';
import { CentrosAtencionComponent } from './centro/centros-atencion.component';
import { HomeComponent } from './home/home.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { EspecialidadDetailComponent } from './especialidades/especialidades-detail.component';
import { ConsultorioComponent } from './consultorio/consultorio.component';
import { ConsultorioDetailComponent } from './consultorio/consultorio-detail.component';
import { MedicosComponent } from './medico/medicos.component';
import { MedicoDetailComponent } from './medico/medico-detail.component';
import { CentroEspecialidadesComponent } from './centro-especialidades/centro-especialidades.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    
    {path: 'centros_atencion', component: CentrosAtencionComponent},
    {path: 'centros_atencion/new', component: CentroAtencionDetailComponent},
    {path: 'centros_atencion/:id', component: CentroAtencionDetailComponent}, 

    {path: 'especialidades', component: EspecialidadesComponent},
    {path: 'especialidades/new', component: EspecialidadDetailComponent},
    {path: 'especialidades/:id', component: EspecialidadDetailComponent} ,

    {path: 'centros_atencion/:id/consultorios', component: ConsultorioComponent},
    {path: 'centros_atencion/:id/consultorios/new', component: ConsultorioDetailComponent},
    {path: 'centros_atencion/:id/consultorios/:idConsultorio', component: ConsultorioDetailComponent},
    
    {path: 'centros_atencion/:id/especialidades', component: CentroEspecialidadesComponent},
    
    {path: 'medicos', component: MedicosComponent},
    {path: 'medicos/new', component: MedicoDetailComponent},
    {path: 'medicos/:id', component: MedicoDetailComponent},
];