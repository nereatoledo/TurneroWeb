# language: es

Característica: Asignación de Turnos

Escenario: Paciente solicita un turno con un médico disponible
Dado que el paciente "Juan Pérez" está registrado en el sistema
Y ha seleccionado la especialidad "Cardiología"
Y ha seleccionado al médico "Dr. Roberto Gómez"
Y ha seleccionado el centro de atención "Centro Médico Integral"
Cuando solicita un turno
Entonces el sistema asigna un turno basado en la disponibilidad
Y el turno queda registrado en el sistema con estado "Programado"
Y el paciente recibe una notificación con la confirmación del turno

Escenario: Paciente solicita un turno pero el médico seleccionado no tiene disponibilidad
Dado que el paciente "María López" está registrado en el sistema
Y ha seleccionado la especialidad "Pediatría"
Y ha seleccionado al médico "Dra. Laura Sánchez"
Y ha seleccionado el centro de atención "Trelew Salud"
Cuando solicita un turno
Pero la agenda de la Dra. Laura Sánchez está completa
Entonces el sistema sugiere otros médicos disponibles en la misma especialidad
Y ofrece fechas alternativas en otros centros de atención
Y el paciente puede elegir entre las opciones sugeridas

Escenario: Paciente solicita un turno pero el centro no tiene disponibilidad en la especialidad
Dado que el paciente "Carlos Rodríguez" está registrado en el sistema
Y ha seleccionado la especialidad "Dermatología"
Y ha seleccionado el centro de atención "Clinica Rawson"
Cuando solicita un turno
Pero no hay disponibilidad en esa especialidad en el centro seleccionado
Entonces el sistema sugiere otros centros donde haya disponibilidad
Y el paciente puede elegir entre las opciones sugeridas

Escenario: Paciente confirma su turno asignado
Dado que el paciente "Lucía Fernández" tiene un turno en estado "Programado"
Cuando accede al sistema y confirma el turno
Entonces el turno cambia de estado a "Confirmado"
Y el paciente recibe una notificación de confirmación

Escenario: Paciente cancela su turno antes de 24 horas
Dado que el paciente "Martín González" tiene un turno en estado "Confirmado"
Y falta más de 24 horas para la fecha del turno
Cuando el paciente cancela el turno
Entonces el turno cambia de estado a "Cancelado"
Y el sistema libera el espacio para otro paciente

Escenario: Paciente cancela su turno con menos de 24 horas de anticipación
Dado que el paciente "Ana Martínez" tiene un turno en estado "Confirmado"
Y faltan menos de 24 horas para la fecha del turno
Cuando el paciente cancela el turno
Entonces el turno cambia de estado a "Cancelado"
Y el sistema registra la cancelación tardía

Escenario: Médico cancela un turno y el sistema ofrece reprogramación
Dado que el paciente "Fernando Díaz" tiene un turno con el médico "Dr. Javier Gómez"
Y el médico ha cancelado su agenda para ese día
Cuando el sistema detecta la cancelación
Entonces se notifica al paciente
Y el sistema sugiere fechas alternativas con el mismo médico o con otro profesional de la misma especialidad