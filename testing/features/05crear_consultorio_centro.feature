# language: es

Característica: Crear un Consultorio en un Centro de Atención

Esquema del escenario: Creación de consultorios en un centro de atención
Dado que existe un centro de atención llamado "<centro_atencion>"
Cuando se registra un consultorio con el número <numero> y el nombre "<nombre_consultorio>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
    | centro_atencion            | numero | nombre_consultorio       | status_code | status_text                     |
    | Centro Médico Integral     | 101    | Consultorio Norte        | 200         | Consultorio creado exitosamente |
    | Centro Médico Integral     | 102    | Consultorio Sur          | 200         | Consultorio creado exitosamente |
    | Centro Médico Integral     | 103    | Consultorio Este         | 200         | Consultorio creado exitosamente |
    | Centro Médico Integral     | 104    | Consultorio Oeste        | 200         | Consultorio creado exitosamente |
    | Centro Médico Integral     | 105    | Consultorio Central      | 200         | Consultorio creado exitosamente |
    | Centro de Salud Rawson     | 201    | Consultorio Cardiología  | 200         | Consultorio creado exitosamente |
    | Centro de Salud Rawson     | 202    | Consultorio Dermatología | 200         | Consultorio creado exitosamente |
    | Centro de Salud Rawson     | 203    | Consultorio Neurología   | 200         | Consultorio creado exitosamente |
    | Centro de Salud Rawson     | 204    | Consultorio Odontología  | 200         | Consultorio creado exitosamente |
    | Centro de Salud Rawson     | 205    | Consultorio Ginecología  | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 301    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 302    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 303    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 304    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 305    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 306    | Consultorio 6            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 307    | Consultorio 7            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 308    | Consultorio 8            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 309    | Consultorio 9            | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 310    | Consultorio 10           | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 311    | Consultorio 11           | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 312    | Consultorio 12           | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 313    | Consultorio 13           | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 314    | Consultorio 14           | 200         | Consultorio creado exitosamente |
    | Trelew Salud               | 315    | Consultorio 15           | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 501    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 502    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 503    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 504    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 505    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Centro Médico Esperanza    | 506    | Consultorio 6            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 601    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 602    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 603    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 604    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 605    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 606    | Consultorio 6            | 200         | Consultorio creado exitosamente |
    | Clinica Rawson             | 607    | Consultorio 7            | 200         | Consultorio creado exitosamente |
    | Centro de Rehabilitación   | 701    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Centro de Rehabilitación   | 702    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Centro de Rehabilitación   | 703    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Centro de Rehabilitación   | 704    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Centro de Rehabilitación   | 705    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Instituto Médico Patagonia | 801    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Instituto Médico Patagonia | 802    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Instituto Médico Patagonia | 803    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Instituto Médico Patagonia | 804    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Instituto Médico Patagonia | 805    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Centro Odontológico Rawson | 901    | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Centro Odontológico Rawson | 902    | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Centro Odontológico Rawson | 903    | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Centro Odontológico Rawson | 904    | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Centro Odontológico Rawson | 905    | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1001   | Consultorio 1            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1002   | Consultorio 2            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1003   | Consultorio 3            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1004   | Consultorio 4            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1005   | Consultorio 5            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1006   | Consultorio 6            | 200         | Consultorio creado exitosamente |
    | Centro Médico del Este     | 1007   | Consultorio 7            | 200         | Consultorio creado exitosamente |

Esquema del escenario: Creación de consultorios con conflicto en un centro de atención
Dado que existe un centro de atención llamado "<centro_atencion>"
Cuando se registra un consultorio con el número <numero> y el nombre "<nombre_consultorio>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
    | centro_atencion        | numero | nombre_consultorio     | status_code | status_text                                                        |
    | Centro Médico Integral | 101    | Consultorio Repetido   | 409         | Error: El número de consultorio ya está registrado                 |
    | Centro Médico Integral | 108    |                        | 409         | Error: El nombre del consultorio es obligatorio                    |
    | Centro de Salud Rawson | 201    | Consultorio Repetido   | 409         | Error: El número de consultorio ya está registrado                 |
    | Centro de Salud Rawson | 206    |                        | 409         | Error: El nombre del consultorio es obligatorio                    |
    | Trelew Salud           | 301    | Consultorio Repetido   | 409         | Error: El número de consultorio ya está registrado                 |
    | Trelew Salud           | 316    |                        | 409         | Error: El nombre del consultorio es obligatorio                    |
    | Trelew Salud           | 317    | Consultorio #Especial  | 409         | Error: El nombre del consultorio contiene caracteres no permitidos |
    | Trelew Salud           | 318    | Consultorio sin número | 409         | Error: Debe especificar un número de consultorio válido            |
    | Trelew Salud           | 319    | Consultorio 15         | 409         | Error: El nombre del consultorio ya está registrado                |

