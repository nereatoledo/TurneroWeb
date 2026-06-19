# language: es
Característica: Reprogramacion de turnos

Escenario: Reprogramación exitosa antes del turno programado
Dado que un paciente tiene un turno programado para el "10/03/2025" a las "10:00"
Y el paciente solicita la reprogramación el "08/03/2025"
Cuando el sistema verifica la disponibilidad del médico y consultorio
Entonces el sistema ofrece las fechas y horarios disponibles
Y el paciente selecciona una nueva fecha y horario
Y el sistema actualiza la agenda del médico
Y el paciente recibe una notificación de confirmación
Y el médico recibe una notificación de actualización en su agenda

Escenario: Intento de reprogramación después de la fecha del turno
Dado que un paciente tenía un turno programado para el "10/03/2025" a las "10:00"
Y el paciente solicita la reprogramación el "11/03/2025"
Cuando el sistema valida la solicitud
Entonces el sistema muestra un mensaje de error de reprogramación "No es posible reprogramar un turno pasado"
Y no permite continuar con la reprogramación

Escenario: No hay disponibilidad inmediata para la reprogramación
Dado que un paciente tiene un turno programado para el "10/03/2025" a las "16:00"
Y el paciente solicita la reprogramación el "08/03/2025"
Y el sistema detecta que no hay horarios disponibles con el mismo médico en la misma semana
Cuando el sistema ofrece la primera fecha y horario disponible
Entonces el paciente puede aceptar la nueva fecha o puede cancelar la reprogramación y mantener el turno original

Escenario: Reprogramación rechazada por exceder el límite de cambios
Dado que un paciente ha reprogramado un turno dos veces
Y el paciente intenta una tercera reprogramación
Cuando el sistema valida la solicitud
Entonces el sistema muestra un mensaje de error de reprogramación "No se puede reprogramar más de dos veces el mismo turno"
Y el paciente solo puede cancelar el turno y solicitar uno nuevo

Escenario: El consultorio original no está disponible en la nueva fecha
Dado que un paciente tiene un turno programado en el consultorio "Consultorio 1" del "Centro Médico Integral"
Y el paciente solicita la reprogramación del turno
Y el sistema detecta que el consultorio no está disponible en la nueva fecha
Cuando el sistema busca otro consultorio disponible en el mismo centro
Entonces el turno es reasignado a un nuevo consultorio disponible
Y el paciente recibe una notificación con el cambio de consultorio
Y el médico recibe una actualización en su agenda