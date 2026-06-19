            # language: es

            Característica: Asignacion de turnos

            Esquema del escenario: Paciente solicita un turno en un tiempo especifico
            Dado que el paciente "<nombrePaciente>" "<apellidoPaciente>" está registrado en el sistema
            Y ha seleccionado la especialidad "<especialidad>"
            Y ha seleccionado al médico Dr. "<nombreMedico>" "<apellidoMedico>"
            Y ha seleccionado el centro de atención "<centroAtencion>"
            Y es el momento "<momento>"
            Cuando solicita un turno el dia "<diaTurno>" para las "<horaTurno>"
            Entonces el sistema asigna el turno

            Ejemplos:
            | nombrePaciente | apellidoPaciente | especialidad         | nombreMedico | apellidoMedico | centroAtencion                 | diaTurno   | horaTurno | momento          |
            | Alberto        | Pasos            | Pediatría            | Laura        | Sánchez        | Trelew Salud                   | 2025-04-15 | 08:00     | 2025-04-10T10:00 |
            | Sandra         | Ruiz             | Pediatría            | Laura        | Sánchez        | Trelew Salud                   | 2025-04-15 | 08:20     | 2025-04-09T10:00 |
            | Marco          | Álvarez          | Pediatría            | Laura        | Sánchez        | Trelew Salud                   | 2025-04-15 | 08:40     | 2025-04-12T10:00 |
            | Lucía          | Fernández        | Cardiología          | Juan         | Pérez          | Centro Médico Esperanza        | 2025-05-08 | 08:00     | 2025-05-04T10:00 |
            | Martín         | González         | Diabetología         | Elena        | Suárez         | Centro Médico Integral         | 2025-06-05 | 12:00     | 2025-06-01T10:00 |
            | Ana            | Martínez         | Medicina General     | Sofía        | Méndez         | Centro Médico Integral         | 2025-06-05 | 12:20     | 2025-06-02T10:00 |
            | Edmundo        | Parra            | Urología             | Diego        | Méndez         | Centro Médico Integral         | 2025-05-28 | 12:00     | 2025-05-20T10:00 |
            | Ana            | Fernández        | Medicina General     | Sofía        | Méndez         | Centro Médico Integral         | 2025-06-05 | 12:40     | 2025-05-29T10:00 |
            | Pedro          | Torres           | Cirugía General      | Cecilia      | Sánchez        | Centro Médico Integral         | 2024-03-08 | 16:00     | 2024-03-07T10:00 |
            | Marco          | Álvarez          | Endocrinología       | Nathan       | Drake          | Centro de Rehabilitación       | 2025-03-10 | 10:00     | 2025-03-05T10:00 |
            | Marcela        | Yáñez            | Ginecología          | Gustavo      | González       | Centro de Salud Dr. Juan Perez | 2025-03-10 | 10:00     | 2025-02-25T10:00 |
            | Juan           | Pérez            | Cardiología          | Juan         | Pérez          | Centro Médico Esperanza        | 2025-03-10 | 10:30     | 2025-02-30T10:00 |
            | María          | López            | Pediatría            | Laura        | Sánchez        | Trelew Salud                   | 2026-05-10 | 08:00     | 2026-04-25T10:00 |
            | Carlos         | Ramírez          | Cardiología          | Roberto      | Gómez          | Centro Médico Integral         | 2025-08-11 | 16:00     | 2025-03-11T10:00 |
            | Fernando       | Díaz             | Cirugía Torácica     | Javier       | Gómez          | Centro de Rehabilitación       | 2025-03-10 | 12:00     | 2025-03-11T10:00 |
            | Miguel         | Pérez            | Pediatría            | Laura        | González       | Centro Médico Integral         | 2026-06-25 | 08:00     | 2026-05-27T10:00 |
            | Miguel         | Pérez            | Pediatría            | Laura        | González       | Centro Médico Integral         | 2026-06-26 | 08:00     | 2026-05-27T11:00 |
            | Miguel         | Pérez            | Pediatría            | Mario        | Rodríguez      | Centro Médico Esperanza        | 2026-06-29 | 08:00     | 2026-05-27T12:00 |
            | Miguel         | Pérez            | Pediatría            | Laura        | Sánchez        | Centro Médico Esperanza        | 2026-06-30 | 08:00     | 2026-05-27T13:00 |
            | Verónica       | Castro           | Neurocirugía         | Fernando     | Méndez         | Centro Médico del Este         | 2025-03-10 | 10:00     | 2025-02-30T10:00 |
            | Héctor         | Valdez           | Emergentología       | Cecilia      | Romero         | Centro Médico del Este         | 2025-03-10 | 10:00     | 2025-03-04T10:00 |
            | Lorena         | Guzmán           | Angiología           | Ricardo      | Morales        | Centro Médico Integral         | 2025-03-10 | 16:00     | 2025-03-05T10:00 |
            | Rodolfo        | Tello            | Angiología           | Ricardo      | Morales        | Centro Médico Integral         | 2025-03-11 | 16:00     | 2025-03-06T10:00 |
            | Silvana        | Moreno           | Angiología           | Ricardo      | Morales        | Centro Médico Integral         | 2025-03-11 | 17:00     | 2025-03-07T10:00 |
            | Beatriz        | Acosta           | Angiología           | Ricardo      | Morales        | Centro Médico Integral         | 2025-03-11 | 18:00     | 2025-03-02T10:00 |
            | Valentina      | Lara             | Angiología           | Ricardo      | Morales        | Centro Médico Integral         | 2025-03-11 | 19:00     | 2025-03-03T10:00 |
            | Rocío          | Mendoza          | Medicina del Deporte | Fernando     | Castro         | Centro Médico Integral         | 2025-03-12 | 08:00     | 2025-03-10T10:00 |


            Esquema del escenario: Paciente confirma un turno
            Dado que el paciente "<nombrePaciente>" "<apellidoPaciente>" está registrado en el sistema
            Y que el paciente tiene un turno en estado "PROGRAMADO" con el Dr. "<nombreMedico>" "<apellidoMedico>" especializado en "<especialidad>"
            Cuando accede al sistema y confirma el turno
            Entonces el turno cambia de estado a "CONFIRMADO"

            Ejemplos:
            | nombrePaciente | apellidoPaciente | especialidad     | nombreMedico | apellidoMedico |
            | Martín         | González         | Diabetología     | Elena        | Suárez         |
            | Ana            | Martínez         | Medicina General | Sofía        | Méndez         |
            | Edmundo        | Parra            | Urología         | Diego        | Méndez         |
            | María          | López            | Pediatría        | Laura        | Sánchez        |
            | Carlos         | Ramírez          | Cardiología      | Roberto      | Gómez          |
            | Rodolfo        | Tello            | Angiología       | Ricardo      | Morales        |
            | Silvana        | Moreno           | Angiología       | Ricardo      | Morales        |
            | Beatriz        | Acosta           | Angiología       | Ricardo      | Morales        |
            | Valentina      | Lara             | Angiología       | Ricardo      | Morales        |


            Esquema del escenario: Paciente cancela un turno
            Dado que el paciente "<nombrePaciente>" "<apellidoPaciente>" está registrado en el sistema
            Y que el paciente tiene un turno en estado "PROGRAMADO" con el Dr. "<nombreMedico>" "<apellidoMedico>" especializado en "<especialidad>"
            Cuando accede al sistema y cancela el turno en el momento "<momento>"
            Entonces el turno cambia de estado a "CANCELADO"

            Ejemplos:
            | nombrePaciente | apellidoPaciente | especialidad     | nombreMedico | apellidoMedico | momento          |
            | Ana            | Fernández        | Medicina General | Sofía        | Méndez         | 2025-06-03T10:00 |
            | Miguel         | Pérez            | Pediatría        | Laura        | González       | 2026-05-27T10:00 |
            | Miguel         | Pérez            | Pediatría        | Laura        | González       | 2026-05-27T10:00 |
            | Miguel         | Pérez            | Pediatría        | Mario        | Rodríguez      | 2026-05-27T10:00 |

            Esquema del escenario: Paciente reprograma un turno
            Dado que el paciente "<nombrePaciente>" "<apellidoPaciente>" está registrado en el sistema
            Y que el paciente tiene un turno con el Dr. "<nombreMedico>" "<apellidoMedico>" especializado en "<especialidad>"
            Y es el momento "<momento>"
            Y reprograma el turno para el dia "<diaTurno>" a las "<horaTurno>"
            Entonces el turno cambia de estado a "REAGENDADO"
            Y el paciente confirma el turno

            Ejemplos:
            | nombrePaciente | apellidoPaciente | especialidad         | nombreMedico | apellidoMedico | diaTurno   | horaTurno | momento          |
            | Rocío          | Mendoza          | Medicina del Deporte | Fernando     | Castro         | 13/03/2025 | 08:00     | 2025-03-11T10:00 |
            | Rocío          | Mendoza          | Medicina del Deporte | Fernando     | Castro         | 14/03/2025 | 08:00     | 2025-03-12T10:00 |