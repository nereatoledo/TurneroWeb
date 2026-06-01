            # language: es

            Característica: Preparación de datos para Agenda

            Esquema del escenario: Añadir médicos faltantes para las pruebas de esquemas de turnos
            Dado que existe un sistema de gestión de centros de atención
            Cuando el administrador asocia el médico con "<nombre>", "<apellido>", <dni>, "<matrícula>" y "<especialidad>" al centro de atención "Trelew Salud"
            Entonces el sistema responde con 200 y "Asociación de médico en centro realizada correctamente"

            Ejemplos:
            | nombre  | apellido  | dni      | matrícula | especialidad |
            | Juan    | Pérez     | 35123456 | MP-JP01   | Cardiología  |
            | Roberto | Fernández | 35123460 | MP-RF01   | Oftalmología |

Escenario: Crear la agenda previa de Ana López (Para forzar conflicto con Carlos en Consultorio 2)
Dado que el administrador configura la agenda del "Consultorio 2"
Y define el horario de atención de "09:00" a "13:00" de lunes a viernes
Y asigna al Dr. "Ana López" con especialidad "Pediatría"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200

Escenario: Crear la agenda previa de Roberto Fernández (Para prueba de cancelación)
Dado que el administrador configura la agenda del "Consultorio 6"
Y define el horario de atención de "08:00" a "18:00" de lunes a viernes
Y asigna al Dr. "Roberto Fernández" con especialidad "Oftalmología"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200