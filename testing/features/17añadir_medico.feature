# language: es

Característica: Preparación de datos para Agenda

Escenario: Añadir al Dr. Juan Pérez para las pruebas de esquemas de turnos
Dado que existe un sistema de gestión de centros de atención
Cuando el administrador asocia el médico con "Juan", "Pérez", 35123456, "MP-JP01" y "Cardiología" al centro de atención "Trelew Salud"
Entonces el sistema responde con 200 y "Asociación de médico en centro realizada correctamente"                                         