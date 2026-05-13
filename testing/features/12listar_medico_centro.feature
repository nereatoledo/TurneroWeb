# language: es

Característica: Listar médicos asociadas a todos los centros médicos

Escenario: Recuperar todas los médicos asociadas a todos los centros
Dado que existen médicos asociados a centros médicos en el sistema
Cuando un usuario del sistema solicita la lista de médicos asociados 
Entonces el sistema responde con un JSON de los centros y sus médicos:

"""
{
    "status_code": 200,
    "status_text": "médicos asociados a centros recuperados correctamente",
    "data": [
        {
            "centro_de_atencion": "Centro Médico Integral",
            "medicos": [
                {
                    "nombre": "Gustavo",
                    "apellido": "Torres",
                    "dni": 36681588,
                    "matricula": "44846-4",
                    "especialidad": "Odontología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Rodríguez",
                    "dni": 29730992,
                    "matricula": "94975-9",
                    "especialidad": "Oftalmología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "González",
                    "dni": 23277412,
                    "matricula": "12610-3",
                    "especialidad": "Pediatría"
                },
                {
                    "nombre": "Patricia",
                    "apellido": "Ramírez",
                    "dni": 32844301,
                    "matricula": "57204-2",
                    "especialidad": "Clínica Médica"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Castro",
                    "dni": 33506029,
                    "matricula": "66669-5",
                    "especialidad": "Medicina del Deporte"
                },
                {
                    "nombre": "Sofía",
                    "apellido": "Méndez",
                    "dni": 38197809,
                    "matricula": "69241-8",
                    "especialidad": "Medicina General"
                },
                {
                    "nombre": "Elena",
                    "apellido": "Suárez",
                    "dni": 26530118,
                    "matricula": "93163-3",
                    "especialidad": "Diabetología"
                },
                {
                    "nombre": "Verónica",
                    "apellido": "López",
                    "dni": 33676932,
                    "matricula": "97483-6",
                    "especialidad": "Endoscopía Digestiva"
                },
                {
                    "nombre": "Pedro",
                    "apellido": "Vargas",
                    "dni": 35877839,
                    "matricula": "15112-5",
                    "especialidad": "Ginecología"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Méndez",
                    "dni": 20441229,
                    "matricula": "99768-3",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Ricardo",
                    "apellido": "Morales",
                    "dni": 40664166,
                    "matricula": "35675-0",
                    "especialidad": "Angiología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Sánchez",
                    "dni": 31994348,
                    "matricula": "76992-4",
                    "especialidad": "Otorrinolaringología"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Sánchez",
                    "dni": 41896995,
                    "matricula": "85312-7",
                    "especialidad": "Cirugía General"
                },
                {
                    "nombre": "Elena",
                    "apellido": "Romero",
                    "dni": 33267640,
                    "matricula": "69490-9",
                    "especialidad": "Medicina Esteticista"
                }
            ]
        },
        {
            "centro_de_atencion": "Centro de Salud Dr. Juan Perez",
            "medicos": [
                {
                    "nombre": "Pedro",
                    "apellido": "Silva",
                    "dni": 22720084,
                    "matricula": "39057-9",
                    "especialidad": "Oftalmología"
                },
                {
                    "nombre": "María",
                    "apellido": "López",
                    "dni": 28092860,
                    "matricula": "39100-1",
                    "especialidad": "Odontología"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Suárez",
                    "dni": 38925485,
                    "matricula": "47262-8",
                    "especialidad": "Clínica Médica"
                },
                {
                    "nombre": "Ana",
                    "apellido": "Ramírez",
                    "dni": 24502601,
                    "matricula": "72945-0",
                    "especialidad": "Fisiatría"
                },
                {
                    "nombre": "Pedro",
                    "apellido": "Silva",
                    "dni": 31232851,
                    "matricula": "46280-1",
                    "especialidad": "Medicina Interna"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Sánchez",
                    "dni": 35827626,
                    "matricula": "33027-7",
                    "especialidad": "Neonatología"
                },
                {
                    "nombre": "Martín",
                    "apellido": "Suárez",
                    "dni": 33628824,
                    "matricula": "41378-3",
                    "especialidad": "Cirugía Vascular"
                },
                {
                    "nombre": "Gustavo",
                    "apellido": "Pérez",
                    "dni": 38575678,
                    "matricula": "49274-9",
                    "especialidad": "Medicina Familiar"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "López",
                    "dni": 37411336,
                    "matricula": "62561-7",
                    "especialidad": "Genética Médica"
                },
                {
                    "nombre": "Patricia",
                    "apellido": "Vargas",
                    "dni": 34991094,
                    "matricula": "37457-7",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Javier",
                    "apellido": "Sánchez",
                    "dni": 27427954,
                    "matricula": "26417-6",
                    "especialidad": "Medicina Forense"
                }
            ]
        },
        {
            "centro_de_atencion": "Trelew Salud",
            "medicos": [
                {
                    "nombre": "Pedro",
                    "apellido": "Castro",
                    "dni": 27514920,
                    "matricula": "30051-3",
                    "especialidad": "Cardiología"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "López",
                    "dni": 34327560,
                    "matricula": "94765-8",
                    "especialidad": "Odontología"
                },
                {
                    "nombre": "Juan",
                    "apellido": "Torres",
                    "dni": 29664654,
                    "matricula": "47376-0",
                    "especialidad": "Dermatología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "López",
                    "dni": 43251052,
                    "matricula": "97620-9",
                    "especialidad": "Ginecología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Suárez",
                    "dni": 36051990,
                    "matricula": "40154-1",
                    "especialidad": "Ortopedia y Traumatología"
                },
                {
                    "nombre": "Ricardo",
                    "apellido": "Ramírez",
                    "dni": 28212316,
                    "matricula": "42823-4",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Díaz",
                    "dni": 34280407,
                    "matricula": "85426-0",
                    "especialidad": "Hepatología"
                },
                {
                    "nombre": "Patricia",
                    "apellido": "Díaz",
                    "dni": 26632849,
                    "matricula": "70839-8",
                    "especialidad": "Geriatría"
                },
                {
                    "nombre": "Martín",
                    "apellido": "Rojas",
                    "dni": 32860322,
                    "matricula": "33905-6",
                    "especialidad": "Fisiatría"
                },
                {
                    "nombre": "Ana",
                    "apellido": "Silva",
                    "dni": 25294692,
                    "matricula": "94013-6",
                    "especialidad": "Nutrición"
                },
                {
                    "nombre": "Sofía",
                    "apellido": "Suárez",
                    "dni": 32037672,
                    "matricula": "18236-8",
                    "especialidad": "Otorrinolaringología"
                },
                {
                    "nombre": "Ricardo",
                    "apellido": "González",
                    "dni": 26780884,
                    "matricula": "44480-0",
                    "especialidad": "Endoscopía Digestiva"
                },
                {
                    "nombre": "Martín",
                    "apellido": "Pérez",
                    "dni": 31105782,
                    "matricula": "20735-0",
                    "especialidad": "Infectología"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Morales",
                    "dni": 39682518,
                    "matricula": "80511-8",
                    "especialidad": "Cirugía Cardiovascular"
                }
            ]
        },
        {
            "centro_de_atencion": "Centro Médico Esperanza",
            "medicos": [
                {
                    "nombre": "Martín",
                    "apellido": "Suárez",
                    "dni": 40456304,
                    "matricula": "23049-2",
                    "especialidad": "Cardiología"
                },
                {
                    "nombre": "Gustavo",
                    "apellido": "González",
                    "dni": 41354734,
                    "matricula": "50771-3",
                    "especialidad": "Ginecología"
                },
                {
                    "nombre": "Verónica",
                    "apellido": "Torres",
                    "dni": 43518736,
                    "matricula": "17287-9",
                    "especialidad": "Odontología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Suárez",
                    "dni": 22560933,
                    "matricula": "26225-4",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Roberto",
                    "apellido": "Castro",
                    "dni": 22703709,
                    "matricula": "33470-6",
                    "especialidad": "Medicina Materno-Fetal"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Torres",
                    "dni": 43987268,
                    "matricula": "69531-0",
                    "especialidad": "Ortopedia y Traumatología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Morales",
                    "dni": 39839550,
                    "matricula": "69989-4",
                    "especialidad": "Neumonología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Morales",
                    "dni": 27175400,
                    "matricula": "77746-6",
                    "especialidad": "Cirugía Plástica"
                },
                {
                    "nombre": "Patricia",
                    "apellido": "Sánchez",
                    "dni": 26470521,
                    "matricula": "45511-4",
                    "especialidad": "Endoscopía Digestiva"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 29983797,
                    "matricula": "56654-7",
                    "especialidad": "Neonatología"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Fernández",
                    "dni": 34745100,
                    "matricula": "97435-8",
                    "especialidad": "Medicina del Trabajo"
                }
            ]
        },
        {
            "centro_de_atencion": "Clinica Rawson",
            "medicos": [
                {
                    "nombre": "Laura",
                    "apellido": "González",
                    "dni": 22640574,
                    "matricula": "13311-4",
                    "especialidad": "Cardiología"
                },
                {
                    "nombre": "Roberto",
                    "apellido": "Pérez",
                    "dni": 35234163,
                    "matricula": "74039-0",
                    "especialidad": "Hematología"
                },
                {
                    "nombre": "Patricia",
                    "apellido": "Ortiz",
                    "dni": 43121366,
                    "matricula": "25763-9",
                    "especialidad": "Hepatología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                },
                {
                    "nombre": "Pedro",
                    "apellido": "Pérez",
                    "dni": 42279752,
                    "matricula": "58062-0",
                    "especialidad": "Anatomía Patológica"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Morales",
                    "dni": 26046382,
                    "matricula": "56617-0",
                    "especialidad": "Medicina Esteticista"
                },
                {
                    "nombre": "Ana",
                    "apellido": "Méndez",
                    "dni": 26399530,
                    "matricula": "80565-9",
                    "especialidad": "Diabetología"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Romero",
                    "dni": 38239159,
                    "matricula": "78401-8",
                    "especialidad": "Medicina Forense"
                },
                {
                    "nombre": "Ana",
                    "apellido": "Fernández",
                    "dni": 20685893,
                    "matricula": "89775-2",
                    "especialidad": "Cirugía Plástica"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                }
            ]
        },
        {
            "centro_de_atencion": "Centro de Rehabilitación",
            "medicos": [
                {
                    "nombre": "Gabriela",
                    "apellido": "Vargas",
                    "dni": 29364849,
                    "matricula": "94366-6",
                    "especialidad": "Psiquiatría"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Torres",
                    "dni": 22330414,
                    "matricula": "60233-5",
                    "especialidad": "Medicina del Deporte"
                },
                {
                    "nombre": "Elena",
                    "apellido": "Rodríguez",
                    "dni": 30322166,
                    "matricula": "69601-1",
                    "especialidad": "Anestesiología"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Castro",
                    "dni": 37544297,
                    "matricula": "44669-5",
                    "especialidad": "Cirugía Vascular"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Méndez",
                    "dni": 39396719,
                    "matricula": "49824-7",
                    "especialidad": "Medicina General"
                },
                {
                    "nombre": "Ana",
                    "apellido": "Gómez",
                    "dni": 33386601,
                    "matricula": "81637-7",
                    "especialidad": "Nefrología"
                },
                {
                    "nombre": "Martín",
                    "apellido": "Silva",
                    "dni": 29684163,
                    "matricula": "73684-9",
                    "especialidad": "Medicina Forense"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                }
            ]
        },
        {
            "centro_de_atencion": "Instituto Médico Patagonia",
            "medicos": [
                {
                    "nombre": "Cecilia",
                    "apellido": "Morales",
                    "dni": 28771160,
                    "matricula": "99281-1",
                    "especialidad": "Medicina General"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Ramírez",
                    "dni": 24834866,
                    "matricula": "94546-4",
                    "especialidad": "Alergia e Inmunología"
                },
                {
                    "nombre": "Roberto",
                    "apellido": "González",
                    "dni": 34035231,
                    "matricula": "17580-0",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Méndez",
                    "dni": 20441229,
                    "matricula": "99768-3",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Ramírez",
                    "dni": 32049132,
                    "matricula": "16529-6",
                    "especialidad": "Cirugía Maxilofacial"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Morales",
                    "dni": 39839550,
                    "matricula": "69989-4",
                    "especialidad": "Neumonología"
                },
                {
                    "nombre": "Elena",
                    "apellido": "Suárez",
                    "dni": 26530118,
                    "matricula": "93163-3",
                    "especialidad": "Diabetología"
                }
            ]
        },
        {
            "centro_de_atencion": "Centro Odontológico Rawson",
            "medicos": [
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                },
                {
                    "nombre": "Ricardo",
                    "apellido": "Romero",
                    "dni": 42782001,
                    "matricula": "14377-8",
                    "especialidad": "Neumonología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Martínez",
                    "dni": 24045378,
                    "matricula": "23884-7",
                    "especialidad": "Genética Médica"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Rodríguez",
                    "dni": 30882721,
                    "matricula": "54352-5",
                    "especialidad": "Medicina del Deporte"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Ramírez",
                    "dni": 24834866,
                    "matricula": "94546-4",
                    "especialidad": "Alergia e Inmunología"
                },
                {
                    "nombre": "Roberto",
                    "apellido": "González",
                    "dni": 34035231,
                    "matricula": "17580-0",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Méndez",
                    "dni": 20441229,
                    "matricula": "99768-3",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Ramírez",
                    "dni": 32049132,
                    "matricula": "16529-6",
                    "especialidad": "Cirugía Maxilofacial"
                }
            ]
        },
        {
            "centro_de_atencion": "Centro Médico del Este",
            "medicos": [
                {
                    "nombre": "Roberto",
                    "apellido": "González",
                    "dni": 34035231,
                    "matricula": "17580-0",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Ortiz",
                    "dni": 32052018,
                    "matricula": "85463-0",
                    "especialidad": "Medicina Interna"
                },
                {
                    "nombre": "Pedro",
                    "apellido": "Suárez",
                    "dni": 41759309,
                    "matricula": "33695-7",
                    "especialidad": "Cirugía Vascular"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Ramírez",
                    "dni": 24834866,
                    "matricula": "94546-4",
                    "especialidad": "Alergia e Inmunología"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Méndez",
                    "dni": 20441229,
                    "matricula": "99768-3",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Ramírez",
                    "dni": 32049132,
                    "matricula": "16529-6",
                    "especialidad": "Cirugía Maxilofacial"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Morales",
                    "dni": 39839550,
                    "matricula": "69989-4",
                    "especialidad": "Neumonología"
                }
            ]
        }
    ]
    }
"""

Escenario: Recuperar los médicos asociados a un centros médico
Dado que existen médicos asociados a centros médicos en el sistema
Cuando un usuario del sistema solicita la lista de médicos asociadas al centro "Centro Médico del Este"
Entonces el sistema responde con un JSON de medicos:

"""
   {
    "status_code": 200,
    "status_text": "médicos asociados recuperados correctamente",
    "data": [
                {
                    "nombre": "Roberto",
                    "apellido": "González",
                    "dni": 34035231,
                    "matricula": "17580-0",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Lucía",
                    "apellido": "Ortiz",
                    "dni": 32052018,
                    "matricula": "85463-0",
                    "especialidad": "Medicina Interna"
                },
                {
                    "nombre": "Pedro",
                    "apellido": "Suárez",
                    "dni": 41759309,
                    "matricula": "33695-7",
                    "especialidad": "Cirugía Vascular"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Ramírez",
                    "dni": 24834866,
                    "matricula": "94546-4",
                    "especialidad": "Alergia e Inmunología"
                },
                {
                    "nombre": "Fernando",
                    "apellido": "Méndez",
                    "dni": 41305320,
                    "matricula": "53747-0",
                    "especialidad": "Neurocirugía"
                },
                {
                    "nombre": "Cecilia",
                    "apellido": "Romero",
                    "dni": 24204926,
                    "matricula": "51754-6",
                    "especialidad": "Emergentología"
                },
                {
                    "nombre": "Carlos",
                    "apellido": "Díaz",
                    "dni": 28156787,
                    "matricula": "37245-6",
                    "especialidad": "Neurología"
                },
                {
                    "nombre": "Diego",
                    "apellido": "Méndez",
                    "dni": 20441229,
                    "matricula": "99768-3",
                    "especialidad": "Urología"
                },
                {
                    "nombre": "Gabriela",
                    "apellido": "Ramírez",
                    "dni": 32049132,
                    "matricula": "16529-6",
                    "especialidad": "Cirugía Maxilofacial"
                },
                {
                    "nombre": "Laura",
                    "apellido": "Morales",
                    "dni": 39839550,
                    "matricula": "69989-4",
                    "especialidad": "Neumonología"
                }
        ]
    }
"""

