import { Point } from "./point";
import { Consultorio } from "../consultorio/consultorio";
export interface CentroAtencion {
    id?: number;
    nombre: string;
    direccion: string;
    localidad: string;
    provincia: string;
    telefono: string;
    coordenadas: Point;
}