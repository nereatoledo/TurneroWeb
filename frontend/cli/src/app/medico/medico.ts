export interface Medico {
    id?: number;
    nombre: string;
    apellido: string;
    dni: string;
    matricula: string;
    especialidad: {
        id?: number;
        nombre: string;
        intervalo?: number;
    };
}