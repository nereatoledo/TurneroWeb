import { Point } from "./point";

export interface CentroAtencion {
    id?: number;
    nombre: string;
    direccion: string;
    localidad: string;
    provincia: string;
    telefono: string;
    coordenadas: Point;
}