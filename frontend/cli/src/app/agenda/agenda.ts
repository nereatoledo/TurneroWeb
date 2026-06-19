import { Medico } from '../medico/medico';
import { Consultorio } from '../consultorio/consultorio';

export interface SlotDTO {
    horario: string;
    disponible: boolean;
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
    intervalo: number;
    slots: SlotDTO[];
}

export interface AgendaDia {
    fecha: string;
    diaSemana: string;
    agendaDetalles: EsquemaTurnoAgenda[];
    esFeriado: boolean;
}