# language: es

Característica: Configuración de Agendas

Escenario: Crear una agenda sin conflictos
Dado que el administrador configura la agenda del "Consultorio 1"
Y define el horario de atención de "08:00" a "14:00" de lunes a viernes
Y asigna al Dr. "Juan Pérez" con especialidad "Cardiología"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200

Escenario: Intentar asignar horarios en conflicto en el mismo consultorio
Dado que el administrador configura la agenda del "Consultorio 2"
Y define un horario de atención de "09:00" a "13:00" para el Dr. "Ana López"
Y posteriormente intenta asignar al Dr. "Carlos Gómez" de "10:00" a "12:00" en el mismo consultorio
Cuando guarda la configuración
Entonces el sistema muestra un mensaje de error "Conflicto de horarios en el consultorio"
Y devuelve un código de estado 409

Escenario: Intentar asignar a un médico en dos consultorios al mismo tiempo
Dado que el Dr. "Mario Rodríguez" está asignado al "Consultorio 3" de "08:00" a "12:00"
Cuando el administrador intenta asignarlo al "Consultorio 4" en el mismo horario
Entonces el sistema muestra un mensaje de error "El médico ya está asignado en otro consultorio"
Y devuelve un código de estado 409

Escenario: Configurar un día feriado en la agenda
Dado que el administrador configura la agenda del "Consultorio 5"
Y define el horario de atención de "08:00" a "18:00" de lunes a viernes
Cuando agrega el "25 de Mayo" como día feriado
Entonces el sistema guarda la configuración correctamente con código 200

Escenario: Un médico cancela su disponibilidad y se notifican los pacientes
Dado que el Dr. "Roberto Fernández" tiene turnos asignados en el "Consultorio 6"
Y el administrador elimina su disponibilidad por motivos personales
Cuando guarda la configuración
Entonces el sistema notifica a los pacientes afectados
Y ofrece opciones de reprogramación
Y devuelve un código de estado 200