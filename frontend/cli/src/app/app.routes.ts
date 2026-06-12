import { Routes } from '@angular/router';
import { CentroAtencionDetailComponent } from './centro/centro-atencion-detail.component';
import { CentrosAtencionComponent } from './centro/centros-atencion.component';
import { HomeComponent } from './home/home.component';
import { PanelHomeComponent } from './panel-home/panel-home.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { EspecialidadDetailComponent } from './especialidades/especialidades-detail.component';
import { ConsultorioComponent } from './consultorio/consultorio.component';
import { ConsultorioDetailComponent } from './consultorio/consultorio-detail.component';
import { MedicosComponent } from './medico/medicos.component';
import { MedicoDetailComponent } from './medico/medico-detail.component';
import { CentroEspecialidadesComponent } from './centro-especialidades/centro-especialidades.component';
import { CentroMedicosComponent } from './centro-medicos/centro-medicos.component';
import { AgendaComponent } from './agenda/agenda.component';


import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';

export const routes: Routes = [
    { path: '', component: HomeComponent, title: 'Turnero Web' },
    
    { path: 'login', component: LoginComponent, title: 'Iniciar Sesión' },
    { path: 'registro', component: RegistroComponent, title: 'Registro de Paciente' },
    { path: 'registro/:id', component: RegistroComponent, title: 'Editar Perfil' },

    // --- ENTORNO ADMINISTRADOR (ACCESO DIRECTO) ---
    { path: 'admin', component: PanelHomeComponent, title: 'Turnero Web - Panel Admin' },
    { path: 'admin/centros_atencion', component: CentrosAtencionComponent, title: 'Centros de Atención - Admin' },
    { path: 'admin/centros_atencion/agenda', component: AgendaComponent, title: 'Agendas - Admin' },
    { path: 'admin/medicos', component: MedicosComponent, title: 'Médicos - Admin' },
    { path: 'admin/especialidades', component: EspecialidadesComponent, title: 'Especialidades - Admin' },
    
    // Sub-rutas de Centros (ADMINISTRADOR)
    { path: 'admin/centros_atencion/:id/especialidades', component: CentroEspecialidadesComponent, title: 'Especialidades del Centro - Admin' },
    { path: 'admin/centros_atencion/:id/medicos', component: CentroMedicosComponent, title: 'Médicos del Centro - Admin' },
    { path: 'admin/centros_atencion/:id/consultorios', component: ConsultorioComponent, title: 'Consultorios - Admin' },
    { path: 'admin/centros_atencion/:id/consultorios/new', component: ConsultorioDetailComponent, title: 'Nuevo Consultorio' },
    { path: 'admin/centros_atencion/:id/consultorios/:idConsultorio', component: ConsultorioDetailComponent, title: 'Consultorio' },

    // --- ENTORNO USUARIO (ACCESO TRAS LOGIN) ---
    { path: 'usuario', component: PanelHomeComponent, title: 'Turnero Web - Portal Usuario' },
    { path: 'usuario/centros_atencion', component: CentrosAtencionComponent, title: 'Centros de Atención' },
    { path: 'usuario/centros_atencion/agenda', component: AgendaComponent, title: 'Agendas' },
    { path: 'usuario/medicos', component: MedicosComponent, title: 'Médicos' },
    { path: 'usuario/especialidades', component: EspecialidadesComponent, title: 'Especialidades' },

    // Sub-rutas de Centros (USUARIO)
    { path: 'usuario/centros_atencion/:id/especialidades', component: CentroEspecialidadesComponent, title: 'Especialidades del Centro' },
    { path: 'usuario/centros_atencion/:id/medicos', component: CentroMedicosComponent, title: 'Médicos del Centro' },

    // --- RUTAS OPERACIONALES INTERNAS DE EDICIÓN/DETALLES ---
    { path: 'centros_atencion/new', component: CentroAtencionDetailComponent, title: 'Nuevo Centro de Atención' },
    { path: 'centros_atencion/:id', component: CentroAtencionDetailComponent, title: 'Centro de Atención' }, 

    { path: 'especialidades/new', component: EspecialidadDetailComponent, title: 'Nueva Especialidad' },
    { path: 'especialidades/:id', component: EspecialidadDetailComponent, title: 'Especialidad' } ,
    
    { path: 'medicos/new', component: MedicoDetailComponent, title: 'Nuevo Médico' },
    { path: 'medicos/:id', component: MedicoDetailComponent, title: 'Médico' },
];