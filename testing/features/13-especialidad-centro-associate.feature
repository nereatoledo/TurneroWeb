# language: es

Característica: Asociar una Especialidad a un Centro de Atención

Antecedentes:
Dado que existe un sistema de gestión de centros de atención
Y existen 9 centros de atención registrados en el sistema:
| Centro de Atención             |
| Centro Médico Integral         |
| Centro de Salud Dr. Juan Perez |
| Trelew Salud                   |
| Centro Médico Esperanza        |
| Clinica Rawson                 |
| Centro de Rehabilitación       |
| Instituto Médico Patagonia     |
| Centro Odontológico Rawson     |
| Centro Médico del Este         |

Esquema del escenario: Asociar una especialidad a un centro de atención exitosamente

Cuando el administrador asocia la especialidad "<especialidad>" al centro de atención "<centro_de_atencion>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| centro_de_atencion             | especialidad              | status_code | status_text                                                  |
| Centro Médico Integral         | Odontología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Oftalmología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Pediatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Traumatología             | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Clínica Médica            | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Medicina del Deporte      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Medicina General          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Diabetología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Endoscopía Digestiva      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Ginecología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Urología                  | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Angiología                | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Otorrinolaringología      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Cirugía General           | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Integral         | Medicina Esteticista      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Oftalmología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Traumatología             | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Odontología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Clínica Médica            | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Pediatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Fisiatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Medicina Interna          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Angiología                | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Neonatología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Cirugía Vascular          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Obstetricia               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Medicina Familiar         | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Genética Médica           | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Urología                  | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Medicina Forense          | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Oftalmología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Cardiología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Odontología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Dermatología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Ginecología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Ortopedia y Traumatología | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Urología                  | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Hepatología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Geriatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Fisiatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Nutrición                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Otorrinolaringología      | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Endoscopía Digestiva      | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Infectología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Trelew Salud                   | Cirugía Cardiovascular    | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Cardiología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Traumatología             | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Ginecología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Pediatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Odontología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Urología                  | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Medicina Materno-Fetal    | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Ortopedia y Traumatología | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Neumonología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Cirugía Plástica          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Endoscopía Digestiva      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Emergentología            | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Neonatología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Fisiatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro Médico Esperanza        | Medicina del Trabajo      | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Dermatología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Pediatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Ginecología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Cardiología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Odontología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Hematología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Hepatología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Neurología                | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Medicina Familiar         | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Anatomía Patológica       | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Cirugía Cardiovascular    | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Medicina Esteticista      | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Diabetología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Medicina Forense          | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Cirugía Plástica          | 200         | Asociación de especialidad en centro realizada correctamente |
| Clinica Rawson                 | Neurocirugía              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Pediatría                 | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Traumatología             | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Cardiología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Oftalmología              | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Ginecología               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Psiquiatría               | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Medicina del Deporte      | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Anestesiología            | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Cirugía Vascular          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Ortopedia y Traumatología | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Cirugía Cardiovascular    | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Medicina General          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Nefrología                | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Medicina Forense          | 200         | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación       | Hematología               | 200         | Asociación de especialidad en centro realizada correctamente |

| Centro de Rehabilitación | Neurocirugía   | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación | Emergentología | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro de Rehabilitación | Neurología     | 200 | Asociación de especialidad en centro realizada correctamente |

| Instituto Médico Patagonia | Clínica Médica         | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Odontología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Ginecología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Cardiología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Traumatología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Otorrinolaringología   | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Medicina Familiar      | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Medicina Esteticista   | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Anatomía Patológica    | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Infectología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Medicina General       | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Cirugía Cardiovascular | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Urología               | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Diabetología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Hematología            | 200 | Asociación de especialidad en centro realizada correctamente |

| Instituto Médico Patagonia | Neurocirugía          | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Emergentología        | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Neurología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Alergia e Inmunología | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Cirugía Maxilofacial  | 200 | Asociación de especialidad en centro realizada correctamente |
| Instituto Médico Patagonia | Neumonología          | 200 | Asociación de especialidad en centro realizada correctamente |

| Centro Odontológico Rawson | Dermatología         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Cardiología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Odontología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Oftalmología         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Ginecología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Neurocirugía         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Gastroenterología    | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Infectología         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Neumonología         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Pediatría            | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Fisiatría            | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Genética Médica      | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Radiología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Medicina del Deporte | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Diabetología         | 200 | Asociación de especialidad en centro realizada correctamente |

| Centro Odontológico Rawson | Emergentología        | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Neurología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Alergia e Inmunología | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Urología              | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Odontológico Rawson | Cirugía Maxilofacial  | 200 | Asociación de especialidad en centro realizada correctamente |


| Centro Médico del Este | Cardiología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Traumatología         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Dermatología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Oftalmología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Ginecología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Emergentología        | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Nefrología            | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Infectología          | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Otorrinolaringología  | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Medicina Esteticista  | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Nutrición             | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Medicina del Deporte  | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Medicina Interna      | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Cirugía Vascular      | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Alergia e Inmunología | 200 | Asociación de especialidad en centro realizada correctamente |

| Centro Médico del Este | Neurocirugía         | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Neurología           | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Urología             | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Cirugía Maxilofacial | 200 | Asociación de especialidad en centro realizada correctamente |
| Centro Médico del Este | Neumonología         | 200 | Asociación de especialidad en centro realizada correctamente |


Esquema del escenario: Asociar una especialidad a un centro de atención con conflictos

Cuando el administrador asocia la especialidad "<especialidad>" al centro de atención "<centro_de_atencion>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| centro_de_atencion             | especialidad           | status_code | status_text                           |
| Centro Médico Integral         | Medicina Esteticista   | 409         | Especialidad ya se encuentra asociada |
| Centro de Salud Dr. Juan Perez | Urología               | 409         | Especialidad ya se encuentra asociada |
| Centro Médico Integggggral     | Medicina Esteticista   | 409         | No existe el Centro Médico            |
| Centro de Salud Rawsonnnnnnnn  | Medicina Esteticista   | 409         | No existe el Centro Médico            |
| Centro Médico Integral         | Medicina Estéééééética | 409         | No existe la especialidad             |
| Centro de Salud Dr. Juan Perez | xxx                    | 409         | No existe la especialidad             |