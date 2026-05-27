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
import { CentroMedicosComponent } from './centro-medicos/centro-medicos.component';

export const routes: Routes = [
    {path: '', component: HomeComponent, title: 'Turnero Web'},
    
    {path: 'centros_atencion', component: CentrosAtencionComponent, title: 'Centros de Atención'},
    {path: 'centros_atencion/new', component: CentroAtencionDetailComponent, title: 'Nuevo Centro de Atención'},
    {path: 'centros_atencion/:id', component: CentroAtencionDetailComponent, title: 'Centro de Atención'}, 

    {path: 'especialidades', component: EspecialidadesComponent, title: 'Especialidades'},
    {path: 'especialidades/new', component: EspecialidadDetailComponent, title: 'Nueva Especialidad'},
    {path: 'especialidades/:id', component: EspecialidadDetailComponent, title: 'Especialidad'} ,

    {path: 'centros_atencion/:id/consultorios', component: ConsultorioComponent, title: 'Consultorios'},
    {path: 'centros_atencion/:id/consultorios/new', component: ConsultorioDetailComponent, title: 'Nuevo Consultorio'},
    {path: 'centros_atencion/:id/consultorios/:idConsultorio', component: ConsultorioDetailComponent, title: 'Consultorio'},
    
    {path: 'centros_atencion/:id/especialidades', component: CentroEspecialidadesComponent, title: 'Especialidades del Centro de Atención'},
    {path: 'centros_atencion/:id/medicos', component: CentroMedicosComponent, title: 'Médicos del Centro de Atención'},
    
    {path: 'medicos', component: MedicosComponent, title: 'Médicos'},
    {path: 'medicos/new', component: MedicoDetailComponent, title: 'Nuevo Médico'},
    {path: 'medicos/:id', component: MedicoDetailComponent, title: 'Médico'},
];