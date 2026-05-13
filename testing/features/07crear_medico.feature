      # language: es

      Característica: Crear médico

      Esquema del escenario: Crear un médico
      Cuando el administrador crea un médico con "<nombre>", "<apellido>", "<dni>", "<matricula>" y "<especialidad>"
      Entonces el sistema responde con <status_code> y "<status_text>"

      Ejemplos:
      | nombre   | apellido  | dni        | matricula | especialidad              | status_code | status_text                                |
      | Martín   | Pérez     | 31105782   | 20735-0   | Infectología              | 200         | Médico Ingresado Correctamente             |
      | Pedro    | López     | 40830678   | 87698-3   | Reumatología              | 200         | Médico Ingresado Correctamente             |
      | Martín   | López     | 41496804   | 52188-5   | Geriatría                 | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | Morales   | 28771160   | 99281-1   | Medicina General          | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Rojas     | 43223623   | 47904-3   | Medicina Materno-Fetal    | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Martínez  | 43137018   | 19123-8   | Emergentología            | 200         | Médico Ingresado Correctamente             |
      | Laura    | González  | 22640574   | 13311-4   | Cardiología               | 200         | Médico Ingresado Correctamente             |
      | Laura    | Méndez    | 21435910   | 68796-7   | Medicina Interna          | 200         | Médico Ingresado Correctamente             |
      | Laura    | Méndez    | 21383552   | 81701-9   | Emergentología            | 200         | Médico Ingresado Correctamente             |
      | Verónica | Torres    | 43518736   | 17287-9   | Odontología               | 200         | Médico Ingresado Correctamente             |
      | Carlos   | Díaz      | 29983797   | 56654-7   | Neonatología              | 200         | Médico Ingresado Correctamente             |
      | Lucía    | Méndez    | 39396719   | 49824-7   | Medicina General          | 200         | Médico Ingresado Correctamente             |
      | Laura    | Suárez    | 22560933   | 26225-4   | Urología                  | 200         | Médico Ingresado Correctamente             |
      | Patricia | Díaz      | 26632849   | 70839-8   | Geriatría                 | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Castro    | 33197365   | 10764-3   | Alergia e Inmunología     | 200         | Médico Ingresado Correctamente             |
      | Martín   | Suárez    | 40456304   | 23049-2   | Cardiología               | 200         | Médico Ingresado Correctamente             |
      | Ana      | Fernández | 20685893   | 89775-2   | Cirugía Plástica          | 200         | Médico Ingresado Correctamente             |
      | Patricia | Sánchez   | 26470521   | 45511-4   | Endoscopía Digestiva      | 200         | Médico Ingresado Correctamente             |
      | Sofía    | Suárez    | 32037672   | 18236-8   | Otorrinolaringología      | 200         | Médico Ingresado Correctamente             |
      | Fernando | Suárez    | 38925485   | 47262-8   | Clínica Médica            | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Pérez     | 42279752   | 58062-0   | Anatomía Patológica       | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | González  | 26780884   | 44480-0   | Endoscopía Digestiva      | 200         | Médico Ingresado Correctamente             |
      | Ana      | Gómez     | 33386601   | 81637-7   | Nefrología                | 200         | Médico Ingresado Correctamente             |
      | Gustavo  | González  | 41354734   | 50771-3   | Ginecología               | 200         | Médico Ingresado Correctamente             |
      | Roberto  | Pérez     | 35234163   | 74039-0   | Hematología               | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Gómez     | 20885569   | 74560-7   | Oncología                 | 200         | Médico Ingresado Correctamente             |
      | Sofía    | Martínez  | 34314596   | 71854-7   | Medicina Forense          | 200         | Médico Ingresado Correctamente             |
      | Lucía    | Fernández | 34745100   | 97435-8   | Medicina del Trabajo      | 200         | Médico Ingresado Correctamente             |
      | Martín   | Silva     | 29684163   | 73684-9   | Medicina Forense          | 200         | Médico Ingresado Correctamente             |
      | Carlos   | López     | 43251052   | 97620-9   | Ginecología               | 200         | Médico Ingresado Correctamente             |
      | Elena    | Rodríguez | 30322166   | 69601-1   | Anestesiología            | 200         | Médico Ingresado Correctamente             |
      | Roberto  | Castro    | 22703709   | 33470-6   | Medicina Materno-Fetal    | 200         | Médico Ingresado Correctamente             |
      | Sofía    | Méndez    | 38197809   | 69241-8   | Medicina General          | 200         | Médico Ingresado Correctamente             |
      | Lucía    | Morales   | 39682518   | 80511-8   | Cirugía Cardiovascular    | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Torres    | 43987268   | 69531-0   | Ortopedia y Traumatología | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Ramírez   | 28212316   | 42823-4   | Urología                  | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Silva     | 22720084   | 39057-9   | Oftalmología              | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | Díaz      | 40756842   | 60702-0   | Hepatología               | 200         | Médico Ingresado Correctamente             |
      | Roberto  | Castro    | 39108499   | 63642-1   | Cirugía Torácica          | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Morales   | 40664166   | 35675-0   | Angiología                | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | Sánchez   | 41896995   | 85312-7   | Cirugía General           | 200         | Médico Ingresado Correctamente             |
      | Martín   | Rojas     | 32860322   | 33905-6   | Fisiatría                 | 200         | Médico Ingresado Correctamente             |
      | Ana      | Pérez     | 38060705   | 55469-1   | Genética Médica           | 200         | Médico Ingresado Correctamente             |
      | Ana      | Méndez    | 26399530   | 80565-9   | Diabetología              | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Sánchez   | 35827626   | 33027-7   | Neonatología              | 200         | Médico Ingresado Correctamente             |
      | Lucía    | Romero    | 38239159   | 78401-8   | Medicina Forense          | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | Morales   | 26046382   | 56617-0   | Medicina Esteticista      | 200         | Médico Ingresado Correctamente             |
      | Diego    | Ortiz     | 36563895   | 11198-5   | Genética Médica           | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Fernández | 40620004   | 13954-5   | Hepatología               | 200         | Médico Ingresado Correctamente             |
      | Verónica | López     | 33676932   | 97483-6   | Endoscopía Digestiva      | 200         | Médico Ingresado Correctamente             |
      | María    | Romero    | 21168828   | 73234-1   | Genética Médica           | 200         | Médico Ingresado Correctamente             |
      | Patricia | Ramírez   | 32844301   | 57204-2   | Clínica Médica            | 200         | Médico Ingresado Correctamente             |
      | Carlos   | Rodríguez | 29730992   | 94975-9   | Oftalmología              | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Vargas    | 29364849   | 94366-6   | Psiquiatría               | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Sánchez   | 41471017   | 64144-4   | Cirugía Vascular          | 200         | Médico Ingresado Correctamente             |
      | Diego    | Rodríguez | 30882721   | 54352-5   | Medicina del Deporte      | 200         | Médico Ingresado Correctamente             |
      | María    | Torres    | 41716039   | 76285-3   | Oncología                 | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Sánchez   | 31994348   | 76992-4   | Otorrinolaringología      | 200         | Médico Ingresado Correctamente             |
      | Ana      | Ramírez   | 24502601   | 72945-0   | Fisiatría                 | 200         | Médico Ingresado Correctamente             |
      | Elena    | Romero    | 33267640   | 69490-9   | Medicina Esteticista      | 200         | Médico Ingresado Correctamente             |
      | Laura    | Torres    | 22330414   | 60233-5   | Medicina del Deporte      | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Rodríguez | 40220839   | 90446-5   | Reumatología              | 200         | Médico Ingresado Correctamente             |
      | Juan     | Torres    | 31993158   | 20376-9   | Cirugía Maxilofacial      | 200         | Médico Ingresado Correctamente             |
      | Laura    | Suárez    | 36051990   | 40154-1   | Ortopedia y Traumatología | 200         | Médico Ingresado Correctamente             |
      | Patricia | Ortiz     | 43121366   | 25763-9   | Hepatología               | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Suárez    | 41759309   | 33695-7   | Cirugía Vascular          | 200         | Médico Ingresado Correctamente             |
      | Ana      | Silva     | 25294692   | 94013-6   | Nutrición                 | 200         | Médico Ingresado Correctamente             |
      | Lucía    | Ortiz     | 32052018   | 85463-0   | Medicina Interna          | 200         | Médico Ingresado Correctamente             |
      | Laura    | Martínez  | 24045378   | 23884-7   | Genética Médica           | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Silva     | 31232851   | 46280-1   | Medicina Interna          | 200         | Médico Ingresado Correctamente             |
      | Fernando | Castro    | 37544297   | 44669-5   | Cirugía Vascular          | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Vargas    | 35877839   | 15112-5   | Ginecología               | 200         | Médico Ingresado Correctamente             |
      | Martín   | Suárez    | 33628824   | 41378-3   | Cirugía Vascular          | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | López     | 34327560   | 94765-8   | Odontología               | 200         | Médico Ingresado Correctamente             |
      | Laura    | González  | 23277412   | 12610-3   | Pediatría                 | 200         | Médico Ingresado Correctamente             |
      | Carlos   | Morales   | 27175400   | 77746-6   | Cirugía Plástica          | 200         | Médico Ingresado Correctamente             |
      | Juan     | Torres    | 29664654   | 47376-0   | Dermatología              | 200         | Médico Ingresado Correctamente             |
      | Carlos   | López     | 37411336   | 62561-7   | Genética Médica           | 200         | Médico Ingresado Correctamente             |
      | Roberto  | Ramírez   | 22799659   | 92046-4   | Neurología                | 200         | Médico Ingresado Correctamente             |
      | Javier   | Sánchez   | 27427954   | 26417-6   | Medicina Forense          | 200         | Médico Ingresado Correctamente             |
      | Patricia | Vargas    | 34991094   | 37457-7   | Urología                  | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Díaz      | 34280407   | 85426-0   | Hepatología               | 200         | Médico Ingresado Correctamente             |
      | Ricardo  | Romero    | 42782001   | 14377-8   | Neumonología              | 200         | Médico Ingresado Correctamente             |
      | Fernando | Castro    | 33506029   | 66669-5   | Medicina del Deporte      | 200         | Médico Ingresado Correctamente             |
      | Pedro    | Castro    | 27514920   | 30051-3   | Cardiología               | 200         | Médico Ingresado Correctamente             |
      | María    | López     | 28092860   | 39100-1   | Odontología               | 200         | Médico Ingresado Correctamente             |
      | Sofía    | Ramírez   | 43015697   | 49018-6   | Cirugía Torácica          | 200         | Médico Ingresado Correctamente             |
      | Laura    | Díaz      | 42175076   | 45609-7   | Alergia e Inmunología     | 200         | Médico Ingresado Correctamente             |
      | Gustavo  | Torres    | 36681588   | 44846-4   | Odontología               | 200         | Médico Ingresado Correctamente             |
      | Javier   | Gómez     | 21743323   | 57085-3   | Cirugía Torácica          | 200         | Médico Ingresado Correctamente             |
      | Gustavo  | Pérez     | 38575678   | 49274-9   | Medicina Familiar         | 200         | Médico Ingresado Correctamente             |
      | Elena    | Suárez    | 26530118   | 93163-3   | Diabetología              | 200         | Médico Ingresado Correctamente             |
      | Laura    | Morales   | 39839550   | 69989-4   | Neumonología              | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Ramírez   | 32049132   | 16529-6   | Cirugía Maxilofacial      | 200         | Médico Ingresado Correctamente             |
      | Diego    | Méndez    | 20441229   | 99768-3   | Urología                  | 200         | Médico Ingresado Correctamente             |
      | Roberto  | González  | 34035231   | 17580-0   | Emergentología            | 200         | Médico Ingresado Correctamente             |
      | Laura    | Ramírez   | 24834866   | 94546-4   | Alergia e Inmunología     | 200         | Médico Ingresado Correctamente             |
      | Carlos   | Díaz      | 28156787   | 37245-6   | Neurología                | 200         | Médico Ingresado Correctamente             |
      | Cecilia  | Romero    | 24204926   | 51754-6   | Emergentología            | 200         | Médico Ingresado Correctamente             |
      | Fernando | Méndez    | 41305320   | 53747-0   | Neurocirugía              | 200         | Médico Ingresado Correctamente             |
      | Gabriela | Ramírez   | 32049133   | 16529-7   | Cirugía Maxilofacial      | 200         | Médico Ingresado Correctamente             |
      | Diego    | Méndez    | 20441229   | 99768-3   | Urología                  | 409         | El dni ya existe en el sistema             |
      | Roberto  | González  | 99999999   | 17580-0   | Emergentología            | 409         | La Matrícula ya existe en el sistema       |
      | Laura    | Ramírez   | 248348--66 | 94546-4   | Alergia e Inmunología     | 409         | dni incorrecto, débe contener sólo números |
      | Carlos   | Díaz      |            | 37245-6   | Neurología                | 409         | El dni es obligatorio                      |
      | Cecilia  | Romero    | 24204926   |           | Emergentología            | 409         | La matrícula es obligatoria                |
      | Fernando | Méndez    | 41305320   | 53747-0   | Neurocirugíaaaaaa         | 409         | La especialidad NO existe                  |
      |          | Sánchez   | 8888888    | 888888    | Medicina Forense          | 409         | El Nombre es obligatorio                   |
      | Patricia |           | 77777777   | 777777    | Urología                  | 409         | El apellido es obligatorio                 |