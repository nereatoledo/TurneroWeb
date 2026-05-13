# language: es

Característica: Asociar un médico a un Centro de Atención

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

Esquema del escenario: Asociar un médico a un centro de atención
Cuando el administrador asocia el médico con "<nombre>", "<apellido>", <dni>, "<matrícula>" y "<especialidad>" al centro de atención "<centro_de_atencion>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| centro_de_atencion             | nombre   | apellido  | dni      | matrícula | especialidad              | status_code | status_text                                            |
| Centro Médico Integral         | Gustavo  | Torres    | 36681588 | 44846-4   | Odontología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Carlos   | Rodríguez | 29730992 | 94975-9   | Oftalmología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Laura    | González  | 23277412 | 12610-3   | Pediatría                 | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Patricia | Ramírez   | 32844301 | 57204-2   | Clínica Médica            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Fernando | Castro    | 33506029 | 66669-5   | Medicina del Deporte      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Sofía    | Méndez    | 38197809 | 69241-8   | Medicina General          | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Elena    | Suárez    | 26530118 | 93163-3   | Diabetología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Verónica | López     | 33676932 | 97483-6   | Endoscopía Digestiva      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Pedro    | Vargas    | 35877839 | 15112-5   | Ginecología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Diego    | Méndez    | 20441229 | 99768-3   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Ricardo  | Morales   | 40664166 | 35675-0   | Angiología                | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Gabriela | Sánchez   | 31994348 | 76992-4   | Otorrinolaringología      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Cecilia  | Sánchez   | 41896995 | 85312-7   | Cirugía General           | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Integral         | Elena    | Romero    | 33267640 | 69490-9   | Medicina Esteticista      | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Pedro    | Silva     | 22720084 | 39057-9   | Oftalmología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | María    | López     | 28092860 | 39100-1   | Odontología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Fernando | Suárez    | 38925485 | 47262-8   | Clínica Médica            | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Ana      | Ramírez   | 24502601 | 72945-0   | Fisiatría                 | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Pedro    | Silva     | 31232851 | 46280-1   | Medicina Interna          | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Gabriela | Sánchez   | 35827626 | 33027-7   | Neonatología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Martín   | Suárez    | 33628824 | 41378-3   | Cirugía Vascular          | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Gustavo  | Pérez     | 38575678 | 49274-9   | Medicina Familiar         | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Carlos   | López     | 37411336 | 62561-7   | Genética Médica           | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Patricia | Vargas    | 34991094 | 37457-7   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Salud Dr. Juan Perez | Javier   | Sánchez   | 27427954 | 26417-6   | Medicina Forense          | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Pedro    | Castro    | 27514920 | 30051-3   | Cardiología               | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Cecilia  | López     | 34327560 | 94765-8   | Odontología               | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Juan     | Torres    | 29664654 | 47376-0   | Dermatología              | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Carlos   | López     | 43251052 | 97620-9   | Ginecología               | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Laura    | Suárez    | 36051990 | 40154-1   | Ortopedia y Traumatología | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Ricardo  | Ramírez   | 28212316 | 42823-4   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Gabriela | Díaz      | 34280407 | 85426-0   | Hepatología               | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Patricia | Díaz      | 26632849 | 70839-8   | Geriatría                 | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Martín   | Rojas     | 32860322 | 33905-6   | Fisiatría                 | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Ana      | Silva     | 25294692 | 94013-6   | Nutrición                 | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Sofía    | Suárez    | 32037672 | 18236-8   | Otorrinolaringología      | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Ricardo  | González  | 26780884 | 44480-0   | Endoscopía Digestiva      | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Martín   | Pérez     | 31105782 | 20735-0   | Infectología              | 200         | Asociación de médico en centro realizada correctamente |
| Trelew Salud                   | Lucía    | Morales   | 39682518 | 80511-8   | Cirugía Cardiovascular    | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Martín   | Suárez    | 40456304 | 23049-2   | Cardiología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Gustavo  | González  | 41354734 | 50771-3   | Ginecología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Verónica | Torres    | 43518736 | 17287-9   | Odontología               | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Laura    | Suárez    | 22560933 | 26225-4   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Roberto  | Castro    | 22703709 | 33470-6   | Medicina Materno-Fetal    | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Gabriela | Torres    | 43987268 | 69531-0   | Ortopedia y Traumatología | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Laura    | Morales   | 39839550 | 69989-4   | Neumonología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Carlos   | Morales   | 27175400 | 77746-6   | Cirugía Plástica          | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Patricia | Sánchez   | 26470521 | 45511-4   | Endoscopía Digestiva      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Cecilia  | Romero    | 24204926 | 51754-6   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Carlos   | Díaz      | 29983797 | 56654-7   | Neonatología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico Esperanza        | Lucía    | Fernández | 34745100 | 97435-8   | Medicina del Trabajo      | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Laura    | González  | 22640574 | 13311-4   | Cardiología               | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Roberto  | Pérez     | 35234163 | 74039-0   | Hematología               | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Patricia | Ortiz     | 43121366 | 25763-9   | Hepatología               | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Carlos   | Díaz      | 28156787 | 37245-6   | Neurología                | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Pedro    | Pérez     | 42279752 | 58062-0   | Anatomía Patológica       | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Cecilia  | Morales   | 26046382 | 56617-0   | Medicina Esteticista      | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Ana      | Méndez    | 26399530 | 80565-9   | Diabetología              | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Lucía    | Romero    | 38239159 | 78401-8   | Medicina Forense          | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Ana      | Fernández | 20685893 | 89775-2   | Cirugía Plástica          | 200         | Asociación de médico en centro realizada correctamente |
| Clinica Rawson                 | Fernando | Méndez    | 41305320 | 53747-0   | Neurocirugía              | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Gabriela | Vargas    | 29364849 | 94366-6   | Psiquiatría               | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Laura    | Torres    | 22330414 | 60233-5   | Medicina del Deporte      | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Elena    | Rodríguez | 30322166 | 69601-1   | Anestesiología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Fernando | Castro    | 37544297 | 44669-5   | Cirugía Vascular          | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Lucía    | Méndez    | 39396719 | 49824-7   | Medicina General          | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Ana      | Gómez     | 33386601 | 81637-7   | Nefrología                | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Martín   | Silva     | 29684163 | 73684-9   | Medicina Forense          | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Fernando | Méndez    | 41305320 | 53747-0   | Neurocirugía              | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Cecilia  | Romero    | 24204926 | 51754-6   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro de Rehabilitación       | Carlos   | Díaz      | 28156787 | 37245-6   | Neurología                | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Cecilia  | Morales   | 28771160 | 99281-1   | Medicina General          | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Fernando | Méndez    | 41305320 | 53747-0   | Neurocirugía              | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Cecilia  | Romero    | 24204926 | 51754-6   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Carlos   | Díaz      | 28156787 | 37245-6   | Neurología                | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Laura    | Ramírez   | 24834866 | 94546-4   | Alergia e Inmunología     | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Roberto  | González  | 34035231 | 17580-0   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Diego    | Méndez    | 20441229 | 99768-3   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Gabriela | Ramírez   | 32049132 | 16529-6   | Cirugía Maxilofacial      | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Laura    | Morales   | 39839550 | 69989-4   | Neumonología              | 200         | Asociación de médico en centro realizada correctamente |
| Instituto Médico Patagonia     | Elena    | Suárez    | 26530118 | 93163-3   | Diabetología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Fernando | Méndez    | 41305320 | 53747-0   | Neurocirugía              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Ricardo  | Romero    | 42782001 | 14377-8   | Neumonología              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Laura    | Martínez  | 24045378 | 23884-7   | Genética Médica           | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Diego    | Rodríguez | 30882721 | 54352-5   | Medicina del Deporte      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Cecilia  | Romero    | 24204926 | 51754-6   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Carlos   | Díaz      | 28156787 | 37245-6   | Neurología                | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Laura    | Ramírez   | 24834866 | 94546-4   | Alergia e Inmunología     | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Roberto  | González  | 34035231 | 17580-0   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Diego    | Méndez    | 20441229 | 99768-3   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Centro Odontológico Rawson     | Gabriela | Ramírez   | 32049132 | 16529-6   | Cirugía Maxilofacial      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Roberto  | González  | 34035231 | 17580-0   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Lucía    | Ortiz     | 32052018 | 85463-0   | Medicina Interna          | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Pedro    | Suárez    | 41759309 | 33695-7   | Cirugía Vascular          | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Laura    | Ramírez   | 24834866 | 94546-4   | Alergia e Inmunología     | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Fernando | Méndez    | 41305320 | 53747-0   | Neurocirugía              | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Cecilia  | Romero    | 24204926 | 51754-6   | Emergentología            | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Carlos   | Díaz      | 28156787 | 37245-6   | Neurología                | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Diego    | Méndez    | 20441229 | 99768-3   | Urología                  | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Gabriela | Ramírez   | 32049132 | 16529-6   | Cirugía Maxilofacial      | 200         | Asociación de médico en centro realizada correctamente |
| Centro Médico del Este         | Laura    | Morales   | 39839550 | 69989-4   | Neumonología              | 200         | Asociación de médico en centro realizada correctamente |


Esquema del escenario: Asociar un médico a un centro de atención con conflictos
Cuando el administrador asocia el médico con "<nombre>", "<apellido>", <dni>, "<matrícula>" y "<especialidad>" al centro de atención "<centro_de_atencion>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| centro_de_atencion     | nombre   | apellido | dni      | matrícula | especialidad         | status_code | status_text                                                                   |
| Centro Médico del Este | Gabriela | Ramírez  | 32049132 | 16529-6   | Cirugía Maxilofacial | 409         | Médico en centro ya está asociado al centro de atención                       |
| Centro Médico del Este | Laura    | Morales  | 39839550 | 69989-4   | Neumonología         | 409         | Médico en centro ya está asociado al centro de atención                       |
| Trelew Salud           | Ana      | Silva    | 25294692 | 94013-6   | Nutrición            | 409         | Médico en centro ya está asociado al centro de atención                       |
| Clinica Rawson         | Carlos   | Díaz     | 99999999 | 37245-6   | Neurología           | 409         | Médico no existe con el dni indicado                                          |
| Clinica Rawson         | Pedro    | Pérez    | 42279752 | 999999-0  | Anatomía Patológica  | 409         | Médico no existe con la matrícula indicada                                    |
| Trelew Salud           | Cecilia  | Morales  | 26046382 | 56617-0   | Medicina Esteticista | 409         | La especialidad del médico no se encuentra disponible para el centro de salud |
| Trelew Salud           | Ana      | Méndez   | 26399530 | 80565-9   | Diabetología         | 409         | La especialidad del médico no se encuentra disponible para el centro de salud |