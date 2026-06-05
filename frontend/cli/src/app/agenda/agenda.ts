import { Medico } from '../medico/medico';
import { Consultorio } from '../consultorio/consultorio';

export interface TurnoSlot {
    horario: string;
    estaDisponible: boolean;
}

export interface CentroAtencionInfo {
    nombre: string;
    direccion: string;
    ciudad: string;
    provincia: string;
    telefono: string;
    coordenadas: any;
}

export interface EsquemaTurnoAgenda {
    horaInicio: string;
    horaFin: string;
    medico: Medico;
    centroAtencion: CentroAtencionInfo;
    consultorio: Consultorio;
    turnos: TurnoSlot[];
}

export interface AgendaDia {
    fecha: string;
    diaSemana: string;
    agendaDetalles: EsquemaTurnoAgenda[];
}