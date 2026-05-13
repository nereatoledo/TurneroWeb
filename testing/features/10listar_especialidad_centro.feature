# language: es

Característica: Listar Especialidades asociadas a todos los centros médicos

Escenario: Recuperar todas las especialidades asociadas a todos los centros
Dado que existen especialidades asociadas a centros médicos en el sistema
Cuando un usuario del sistema solicita la lista de especialidades asociadas
Entonces el sistema responde con un JSON de los centros y sus especialidades:

"""
{
"status_code": 200,
"status_text": "especialidades asociadas a centros recuperadas correctamente",
"data": [
        {
            "centro_de_atencion": "Centro Médico Integral",
            "especialidades": [
                "Odontología",
                "Oftalmología",
                "Pediatría",
                "Traumatología",
                "Clínica Médica",
                "Medicina del Deporte",
                "Medicina General",
                "Diabetología",
                "Endoscopía Digestiva",
                "Ginecología",
                "Urología",
                "Angiología",
                "Otorrinolaringología",
                "Cirugía General",
                "Medicina Esteticista"
            ]
        }, {
            "centro_de_atencion": "Centro de Salud Dr. Juan Perez",
            "especialidades": [
                "Oftalmología",
                "Traumatología",
                "Odontología",
                "Clínica Médica",
                "Pediatría",
                "Fisiatría",
                "Medicina Interna",
                "Angiología",
                "Neonatología",
                "Cirugía Vascular",
                "Obstetricia",
                "Medicina Familiar",
                "Genética Médica",
                "Urología",
                "Medicina Forense"
            ]
        }, {
            "centro_de_atencion": "Trelew Salud",
            "especialidades": [
                "Oftalmología",
                "Cardiología",
                "Odontología",
                "Dermatología",
                "Ginecología",
                "Ortopedia y Traumatología",
                "Urología",
                "Hepatología",
                "Geriatría",
                "Fisiatría",
                "Nutrición",
                "Otorrinolaringología",
                "Endoscopía Digestiva",
                "Infectología",
                "Cirugía Cardiovascular"
            ]
        }, {
            "centro_de_atencion": "Centro Médico Esperanza",
            "especialidades": [
                "Cardiología",
                "Traumatología",
                "Ginecología",
                "Pediatría",
                "Odontología",
                "Urología",
                "Medicina Materno-Fetal",
                "Ortopedia y Traumatología",
                "Neumonología",
                "Cirugía Plástica",
                "Endoscopía Digestiva",
                "Emergentología",
                "Neonatología",
                "Fisiatría",
                "Medicina del Trabajo"
            ]
        }, {
            "centro_de_atencion": "Clinica Rawson",
            "especialidades": [
                "Dermatología",
                "Pediatría",
                "Ginecología",
                "Cardiología",
                "Odontología",
                "Hematología",
                "Hepatología",
                "Neurología",
                "Medicina Familiar",
                "Anatomía Patológica",
                "Cirugía Cardiovascular",
                "Medicina Esteticista",
                "Diabetología",
                "Medicina Forense",
                "Cirugía Plástica",
                "Neurocirugía"
            ]
        }, {
            "centro_de_atencion": "Centro de Rehabilitación",
            "especialidades": [
                "Pediatría",
                "Traumatología",
                "Cardiología",
                "Oftalmología",
                "Ginecología",
                "Psiquiatría",
                "Medicina del Deporte",
                "Anestesiología",
                "Cirugía Vascular",
                "Ortopedia y Traumatología",
                "Cirugía Cardiovascular",
                "Medicina General",
                "Nefrología",
                "Medicina Forense",
                "Hematología",
                "Neurocirugía",
                "Emergentología",
                "Neurología"
            ]
        }, {
            "centro_de_atencion": "Instituto Médico Patagonia",
            "especialidades": [
                "Clínica Médica",
                "Odontología",
                "Ginecología",
                "Cardiología",
                "Traumatología",
                "Otorrinolaringología",
                "Medicina Familiar",
                "Medicina Esteticista",
                "Anatomía Patológica",
                "Infectología",
                "Medicina General",
                "Cirugía Cardiovascular",
                "Urología",
                "Diabetología",
                "Hematología",
                "Neurocirugía",
                "Emergentología",
                "Neurología",
                "Alergia e Inmunología",
                "Cirugía Maxilofacial",
                "Neumonología"
            ]
        }, {
            "centro_de_atencion": "Centro Odontológico Rawson",
            "especialidades": [
                "Dermatología",
                "Cardiología",
                "Odontología",
                "Oftalmología",
                "Ginecología",
                "Neurocirugía",
                "Gastroenterología",
                "Infectología",
                "Neumonología",
                "Pediatría",
                "Fisiatría",
                "Genética Médica",
                "Radiología",
                "Medicina del Deporte",
                "Diabetología",
                "Emergentología",
                "Neurología",
                "Alergia e Inmunología",
                "Urología",
                "Cirugía Maxilofacial"
            ]
        }, {
            "centro_de_atencion": "Centro Médico del Este",
            "especialidades": [
                "Cardiología",
                "Traumatología",
                "Dermatología",
                "Oftalmología",
                "Ginecología",
                "Emergentología",
                "Nefrología",
                "Infectología",
                "Otorrinolaringología",
                "Medicina Esteticista",
                "Nutrición",
                "Medicina del Deporte",
                "Medicina Interna",
                "Cirugía Vascular",
                "Alergia e Inmunología",
                "Neurocirugía",
                "Neurología",
                "Urología",
                "Cirugía Maxilofacial",
                "Neumonología"
            ]
        }
    ]
}
"""

Escenario: Recuperar las especialidades asociadas a un centros médico
Dado que existen especialidades asociadas a centros médicos en el sistema
Cuando un usuario del sistema solicita la lista de especialidades asociadas al centro "Centro Médico Integral"
Entonces el sistema responde con un JSON de las especialidades de ese centro:

"""
{
    "status_code": 200,
    "status_text": "especialidades asociadas a centros recuperadas correctamente",
    "data":[
        "Odontología",
        "Oftalmología",
        "Pediatría",
        "Traumatología",
        "Clínica Médica",
        "Medicina del Deporte",
        "Medicina General",
        "Diabetología",
        "Endoscopía Digestiva",
        "Ginecología",
        "Urología",
        "Angiología",
        "Otorrinolaringología",
        "Cirugía General",
        "Medicina Esteticista"
    ]
}
"""
