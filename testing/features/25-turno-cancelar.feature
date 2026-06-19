# language: es
Característica: Cancelación de Turnos

Escenario: Paciente cancela un turno dentro del tiempo permitido
Dado que un paciente tiene un turno programado para "2025-03-10 10:00"
Y el sistema permite cancelaciones hasta 24 horas antes de la cita
Cuando el paciente solicita la cancelación del turno el "2025-03-09 08:00"
Entonces la cancelación se procesa correctamente con código 200
Y el turno se libera en la agenda
Y se envía una notificación al médico y al centro de atención

Escenario: Paciente cancela un turno fuera del tiempo permitido
Dado que un paciente tiene un turno programado para "2025-03-10 10:00"
Y el sistema permite cancelaciones hasta 24 horas antes de la cita
Cuando el paciente solicita la cancelación del turno el "2025-03-10 09:00"
Entonces la cancelación se procesa correctamente con código 200
Y muestra el mensaje "Turno cancelado con menos de 24 horas de anticipación"

Escenario: Se notifica la cancelación al médico y centro de atención
Dado que un paciente cancela un turno programado correctamente
Cuando el sistema procesa la cancelación
Entonces se envía una notificación al médico con el detalle del turno cancelado
Y se notifica al centro de atención sobre la cancelación

Escenario: La cancelación de un turno se registra en el sistema
Dado que un paciente cancela un turno programado correctamente
Cuando el sistema procesa la cancelación
Entonces el historial de turnos del paciente debe reflejar la cancelación
Y el historial del médico debe actualizarse con el turno cancelado

Escenario: Paciente supera el límite de cancelaciones permitidas
Dado que un paciente ha cancelado 3 turnos en los últimos 30 días
Y el sistema tiene una política de restricción tras 3 cancelaciones en un mes
Cuando el paciente intenta cancelar un nuevo turno
Entonces el sistema rechaza la solicitud con código 409
Y muestra el mensaje "Ha superado el límite de cancelaciones permitidas en el último mes"
Y se notifica al centro de atención sobre la incidencia