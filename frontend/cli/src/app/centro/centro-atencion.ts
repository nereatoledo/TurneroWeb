import { Point } from "./point";
import { Consultorio } from "./consultorio";

export interface CentroAtencion {
    id?: number;
    nombre: string;
    direccion: string;
    localidad: string;
    provincia: string;
    telefono: string;
    coordenadas: Point;
    consultorios: Consultorio[];
}