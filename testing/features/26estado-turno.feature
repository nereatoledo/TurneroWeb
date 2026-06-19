# language: es
Característica: Estado de los Turnos

Escenario: Consultar el estado de un turno programado
Dado que el paciente "Juan Pérez" tiene un turno programado el "2025-03-10"
Cuando accede a la sección "Mis Turnos"
Entonces el sistema muestra el estado "PROGRAMADO"
Y se muestra la opción "Confirmar" y "Cancelar"

Escenario: Confirmar un turno programado
Dado que el paciente "Juan Pérez" tiene un turno en estado "PROGRAMADO"
Cuando confirma el turno en la aplicación
Entonces el sistema cambia el estado del turno a "CONFIRMADO"
Y envía una notificación de confirmación al paciente

Escenario: Cancelar un turno confirmado
Dado que el paciente "María López" tiene un turno en estado "CONFIRMADO"
Cuando cancela el turno desde la aplicación
Entonces el sistema cambia el estado del turno a "CANCELADO"
Y se envía una notificación de cancelación al paciente

Escenario: Reagendar un turno debido a indisponibilidad del médico
Dado que el médico "Dr. Roberto Gómez" no podrá atender en la fecha programada
Y el paciente "Carlos Ramírez" tiene un turno con ese médico en estado "CONFIRMADO"
Cuando el sistema reprograma el turno para una nueva fecha
Entonces el sistema cambia el estado del turno a "REAGENDADO"
Y envía una notificación al paciente con la nueva fecha y hora

Escenario: Cancelar un turno no confirmado dentro del plazo límite
Dado que el paciente "Pedro Torres" tiene un turno en estado "PROGRAMADO"
Y no lo ha confirmado 24 horas antes de la cita
Cuando el sistema ejecuta la verificación diaria
Entonces el sistema cambia el estado del turno a "CANCELADO"
Y se envía una notificación de cancelación al paciente

Escenario: Intentar reactivar un turno cancelado
Dado que el paciente "Ana Fernández" tenía un turno en estado "CANCELADO"
Cuando intenta reactivar el turno en la aplicación
Entonces el sistema muestra un mensaje de error "No se puede reactivar un turno cancelado"
Y no permite el cambio de estado
