# language: es

Característica: Listar consultorios

Escenario: Listar consultorios de un centro de atención
Dado que existe un centro de atención llamado "Centro Médico Esperanza"
Cuando se solicita la lista de consultorios del centro
Entonces el sistema responde con el siguiente JSON:
"""
{
    "status_code": 200,
    "status_text": "Consulta exitosa",
    "data": [
        {"numero": 501, "nombre_consultorio": "Consultorio 1"},
        {"numero": 502, "nombre_consultorio": "Consultorio 2"},
        {"numero": 503, "nombre_consultorio": "Consultorio 3"},
        {"numero": 504, "nombre_consultorio": "Consultorio 4"},
        {"numero": 505, "nombre_consultorio": "Consultorio 5"},
        {"numero": 506, "nombre_consultorio": "Consultorio 6"}
    ]
}
"""

Escenario: Listar todos los centros con sus consultorios
Dado que existen múltiples centros de atención registrados
Cuando se solicita la lista completa de centros con sus consultorios
Entonces el sistema responde con el siguiente JSON que contiene los datos:
"""
{
	"status_code": 200,
	"status_text": "Consulta exitosa",
	"data": [
		{
			"centro_atencion": "Centro Médico Integral",
			"consultorios": [
				{"numero": 101, "nombre": "Consultorio Norte"},
				{"numero": 102, "nombre": "Consultorio Sur"},
				{"numero": 103, "nombre": "Consultorio Este"},
				{"numero": 104, "nombre": "Consultorio Oeste"},
				{"numero": 105, "nombre": "Consultorio Central"}
			]
		},
		{
			"centro_atencion": "Centro de Salud Dr. Juan Perez",
			"consultorios": [
				{"numero": 201, "nombre": "Consultorio Cardiología"},
				{"numero": 202, "nombre": "Consultorio Dermatología"},
				{"numero": 203, "nombre": "Consultorio Neurología"},
				{"numero": 204, "nombre": "Consultorio Odontología"},
				{"numero": 205, "nombre": "Consultorio Ginecología"}
			]
		},
		{
			"centro_atencion": "Trelew Salud",
			"consultorios": [
				{"numero": 301, "nombre": "Consultorio 1"},
				{"numero": 302, "nombre": "Consultorio 2"},
				{"numero": 303, "nombre": "Consultorio 3"},
				{"numero": 304, "nombre": "Consultorio 4"},
				{"numero": 305, "nombre": "Consultorio 5"},
				{"numero": 306, "nombre": "Consultorio 6"},
				{"numero": 307, "nombre": "Consultorio 7"},
				{"numero": 308, "nombre": "Consultorio 8"},
				{"numero": 309, "nombre": "Consultorio 9"},
				{"numero": 310, "nombre": "Consultorio 10"},
				{"numero": 311, "nombre": "Consultorio 11"},
				{"numero": 312, "nombre": "Consultorio 12"},
				{"numero": 313, "nombre": "Consultorio 13"},
				{"numero": 314, "nombre": "Consultorio 14"},
				{"numero": 315, "nombre": "Consultorio 15"}
			]
		},
		{
			"centro_atencion": "Centro Médico Esperanza",
			"consultorios": [
				{"numero": 501, "nombre": "Consultorio 1"},
				{"numero": 502, "nombre": "Consultorio 2"},
				{"numero": 503, "nombre": "Consultorio 3"},
				{"numero": 504, "nombre": "Consultorio 4"},
				{"numero": 505, "nombre": "Consultorio 5"},
				{"numero": 506, "nombre": "Consultorio 6"}
			]
		},
		{
			"centro_atencion": "Clinica Rawson",
			"consultorios": [
				{"numero": 601, "nombre": "Consultorio 1"},
				{"numero": 602, "nombre": "Consultorio 2"},
				{"numero": 603, "nombre": "Consultorio 3"},
				{"numero": 604, "nombre": "Consultorio 4"},
				{"numero": 605, "nombre": "Consultorio 5"},
				{"numero": 606, "nombre": "Consultorio 6"},
				{"numero": 607, "nombre": "Consultorio 7"}
			]
		},
		{
			"centro_atencion": "Centro de Rehabilitación",
			"consultorios": [
				{"numero": 701, "nombre": "Consultorio 1"},
				{"numero": 702, "nombre": "Consultorio 2"},
				{"numero": 703, "nombre": "Consultorio 3"},
				{"numero": 704, "nombre": "Consultorio 4"},
				{"numero": 705, "nombre": "Consultorio 5"}
			]
		},
		{
			"centro_atencion": "Instituto Médico Patagonia",
			"consultorios": [
				{"numero": 801, "nombre": "Consultorio 1"},
				{"numero": 802, "nombre": "Consultorio 2"},
				{"numero": 803, "nombre": "Consultorio 3"},
				{"numero": 804, "nombre": "Consultorio 4"},
				{"numero": 805, "nombre": "Consultorio 5"}
			]
		},
		{
			"centro_atencion": "Centro Odontológico Rawson",
			"consultorios": [
				{"numero": 901, "nombre": "Consultorio 1"},
				{"numero": 902, "nombre": "Consultorio 2"},
				{"numero": 903, "nombre": "Consultorio 3"},
				{"numero": 904, "nombre": "Consultorio 4"},
				{"numero": 905, "nombre": "Consultorio 5"}
			]
		},
		{
			"centro_atencion": "Centro Médico del Este",
			"consultorios": [
				{"numero": 1001, "nombre": "Consultorio 1"},
				{"numero": 1002, "nombre": "Consultorio 2"},
				{"numero": 1003, "nombre": "Consultorio 3"},
				{"numero": 1004, "nombre": "Consultorio 4"},
				{"numero": 1005, "nombre": "Consultorio 5"},
				{"numero": 1006, "nombre": "Consultorio 6"},
				{"numero": 1007, "nombre": "Consultorio 7"}
			]
		}
	]
}
"""

Escenario: Intentar listar consultorios de un centro inexistente
Dado que el centro de atención llamado "Centro Inexistente" no está registrado
Cuando se solicita la lista de consultorios del centro "Centro Inexistente"
Entonces el sistema responde con el siguiente JSON vacio:
"""
{
    "status_code": 409,
    "status_text": "Ningún consultorio recuperado",
    "data": []
}
"""
