# language: es

Característica: Listar pacientes

Escenario: Recuperar todas los pacientes registrados en el sistema
Dado que existen 51 pacientes registrados en el sistema
Cuando un usuario del sistema solicita la lista de pacientes
Entonces el sistema responde con un JSON de los pacientes:
"""
{
    "data": [
        {
            "id": 37066,
            "nombre": "Alberto",
            "apellido": "Pasos",
            "dni": "31590782",
            "fechaNacimiento": "30/03/1980",
            "obraSocial": {
                "id": 3525,
                "nombre": "SANARTE",
                "codigo": "SANARTE"
            }
        },
        {
            "id": 37067,
            "nombre": "María",
            "apellido": "García",
            "dni": "28456123",
            "fechaNacimiento": "15/07/1985",
            "obraSocial": {
                "id": 3437,
                "nombre": "A.P.M.",
                "codigo": "APM"
            }
        },
        {
            "id": 37068,
            "nombre": "Juan",
            "apellido": "López",
            "dni": "35789456",
            "fechaNacimiento": "22/11/1978",
            "obraSocial": {
                "id": 3438,
                "nombre": "AMMA OSSACRA",
                "codigo": "AMMAOSS"
            }
        },
        {
            "id": 37069,
            "nombre": "Carlos",
            "apellido": "Rodríguez",
            "dni": "29123456",
            "fechaNacimiento": "08/05/1992",
            "obraSocial": {
                "id": 3439,
                "nombre": "AMPARA SALUD",
                "codigo": "AMPARASAL"
            }
        },
        {
            "id": 37070,
            "nombre": "Ana",
            "apellido": "Martínez",
            "dni": "32654321",
            "fechaNacimiento": "14/02/1988",
            "obraSocial": {
                "id": 3440,
                "nombre": "AMTAC",
                "codigo": "AMTAC"
            }
        },
        {
            "id": 37071,
            "nombre": "Pedro",
            "apellido": "González",
            "dni": "30987654",
            "fechaNacimiento": "03/09/1975",
            "obraSocial": {
                "id": 3442,
                "nombre": "ANDES SALUD",
                "codigo": "ANDESSAL"
            }
        },
        {
            "id": 37072,
            "nombre": "Patricia",
            "apellido": "Fernández",
            "dni": "27345678",
            "fechaNacimiento": "19/12/1990",
            "obraSocial": {
                "id": 3443,
                "nombre": "APROSS",
                "codigo": "APROSS"
            }
        },
        {
            "id": 37073,
            "nombre": "Miguel",
            "apellido": "Pérez",
            "dni": "33456789",
            "fechaNacimiento": "25/06/1982",
            "obraSocial": {
                "id": 3444,
                "nombre": "AVALIAN-ACA SALUD",
                "codigo": "AVALIANACA"
            }
        },
        {
            "id": 37074,
            "nombre": "Laura",
            "apellido": "Sánchez",
            "dni": "31234567",
            "fechaNacimiento": "11/04/1987",
            "obraSocial": {
                "id": 3445,
                "nombre": "BANCARIOS",
                "codigo": "BANCAR"
            }
        },
        {
            "id": 37075,
            "nombre": "Roberto",
            "apellido": "Torres",
            "dni": "28567890",
            "fechaNacimiento": "07/10/1979",
            "obraSocial": {
                "id": 3446,
                "nombre": "BIENESTAR SALUD S.A.",
                "codigo": "BIENESTAR"
            }
        },
        {
            "id": 37076,
            "nombre": "Sandra",
            "apellido": "Ruiz",
            "dni": "34678901",
            "fechaNacimiento": "20/01/1993",
            "obraSocial": {
                "id": 3491,
                "nombre": "OSDE",
                "codigo": "OSDE"
            }
        },
        {
            "id": 37077,
            "nombre": "Diego",
            "apellido": "Díaz",
            "dni": "26789012",
            "fechaNacimiento": "16/08/1981",
            "obraSocial": {
                "id": 3463,
                "nombre": "MEDIFE",
                "codigo": "MEDIFE"
            }
        },
        {
            "id": 37078,
            "nombre": "Claudia",
            "apellido": "Morales",
            "dni": "29890123",
            "fechaNacimiento": "09/03/1989",
            "obraSocial": {
                "id": 3535,
                "nombre": "SWISS MEDICAL",
                "codigo": "SWISSMED"
            }
        },
        {
            "id": 37079,
            "nombre": "Fernando",
            "apellido": "Castro",
            "dni": "32901234",
            "fechaNacimiento": "27/07/1984",
            "obraSocial": {
                "id": 3514,
                "nombre": "PAMI",
                "codigo": "PAMI"
            }
        },
        {
            "id": 37080,
            "nombre": "Gabriela",
            "apellido": "Vargas",
            "dni": "25012345",
            "fechaNacimiento": "05/11/1986",
            "obraSocial": {
                "id": 3537,
                "nombre": "UNION PERSONAL",
                "codigo": "UNIONPERS"
            }
        },
        {
            "id": 37081,
            "nombre": "Marco",
            "apellido": "Álvarez",
            "dni": "25456123",
            "fechaNacimiento": "12/05/1980",
            "obraSocial": {
                "id": 3437,
                "nombre": "A.P.M.",
                "codigo": "APM"
            }
        },
        {
            "id": 37082,
            "nombre": "Verónica",
            "apellido": "Castro",
            "dni": "25197012",
            "fechaNacimiento": "08/11/1983",
            "obraSocial": {
                "id": 3438,
                "nombre": "AMMA OSSACRA",
                "codigo": "AMMAOSS"
            }
        },
        {
            "id": 37083,
            "nombre": "Silvana",
            "apellido": "Moreno",
            "dni": "27123456",
            "fechaNacimiento": "19/07/1981",
            "obraSocial": {
                "id": 3439,
                "nombre": "AMPARA SALUD",
                "codigo": "AMPARASAL"
            }
        },
        {
            "id": 37084,
            "nombre": "Ángel",
            "apellido": "Rojas",
            "dni": "28456789",
            "fechaNacimiento": "14/03/1979",
            "obraSocial": {
                "id": 3440,
                "nombre": "AMTAC",
                "codigo": "AMTAC"
            }
        },
        {
            "id": 37085,
            "nombre": "Gabriela",
            "apellido": "Solís",
            "dni": "29789012",
            "fechaNacimiento": "25/09/1984",
            "obraSocial": {
                "id": 3441,
                "nombre": "AMUR ASSIMRA",
                "codigo": "AMURASS"
            }
        },
        {
            "id": 37086,
            "nombre": "Rodolfo",
            "apellido": "Tello",
            "dni": "30012345",
            "fechaNacimiento": "02/01/1975",
            "obraSocial": {
                "id": 3442,
                "nombre": "ANDES SALUD",
                "codigo": "ANDESSAL"
            }
        },
        {
            "id": 37087,
            "nombre": "Adriana",
            "apellido": "Uribe",
            "dni": "31345678",
            "fechaNacimiento": "16/06/1986",
            "obraSocial": {
                "id": 3443,
                "nombre": "APROSS",
                "codigo": "APROSS"
            }
        },
        {
            "id": 37088,
            "nombre": "Héctor",
            "apellido": "Valdez",
            "dni": "32678901",
            "fechaNacimiento": "30/12/1982",
            "obraSocial": {
                "id": 3444,
                "nombre": "AVALIAN-ACA SALUD",
                "codigo": "AVALIANACA"
            }
        },
        {
            "id": 37089,
            "nombre": "Marcela",
            "apellido": "Yáñez",
            "dni": "33901234",
            "fechaNacimiento": "11/04/1988",
            "obraSocial": {
                "id": 3445,
                "nombre": "BANCARIOS",
                "codigo": "BANCAR"
            }
        },
        {
            "id": 37090,
            "nombre": "Javier",
            "apellido": "Zambrano",
            "dni": "24234567",
            "fechaNacimiento": "23/08/1985",
            "obraSocial": {
                "id": 3446,
                "nombre": "BIENESTAR SALUD S.A.",
                "codigo": "BIENESTAR"
            }
        },
        {
            "id": 37091,
            "nombre": "Beatriz",
            "apellido": "Acosta",
            "dni": "25567890",
            "fechaNacimiento": "07/02/1987",
            "obraSocial": {
                "id": 3447,
                "nombre": "BOREAL",
                "codigo": "BOREAL"
            }
        },
        {
            "id": 37092,
            "nombre": "Ernesto",
            "apellido": "Bonilla",
            "dni": "26890123",
            "fechaNacimiento": "18/10/1980",
            "obraSocial": {
                "id": 3448,
                "nombre": "C.P.C.E.",
                "codigo": "CPCE"
            }
        },
        {
            "id": 37093,
            "nombre": "Gloria",
            "apellido": "Cabrera",
            "dni": "27012345",
            "fechaNacimiento": "05/05/1983",
            "obraSocial": {
                "id": 3449,
                "nombre": "CAJA NOTARIAL",
                "codigo": "CAJANOT"
            }
        },
        {
            "id": 37094,
            "nombre": "Raúl",
            "apellido": "Delgado",
            "dni": "28345678",
            "fechaNacimiento": "21/11/1981",
            "obraSocial": {
                "id": 3450,
                "nombre": "CAMI SALUD",
                "codigo": "CAMISAL"
            }
        },
        {
            "id": 37095,
            "nombre": "Cecilia",
            "apellido": "Espinoza",
            "dni": "29678901",
            "fechaNacimiento": "09/07/1986",
            "obraSocial": {
                "id": 3454,
                "nombre": "DASPU",
                "codigo": "DASPU"
            }
        },
        {
            "id": 37096,
            "nombre": "Vicente",
            "apellido": "Flores",
            "dni": "30901234",
            "fechaNacimiento": "14/03/1984",
            "obraSocial": {
                "id": 3455,
                "nombre": "GALENO",
                "codigo": "GALENO"
            }
        },
        {
            "id": 37097,
            "nombre": "Marisa",
            "apellido": "García",
            "dni": "47984684",
            "fechaNacimiento": "28/09/1979",
            "obraSocial": {
                "id": 3456,
                "nombre": "GASTRONOMICOS",
                "codigo": "GASTRON"
            }
        },
        {
            "id": 37098,
            "nombre": "Augusto",
            "apellido": "Gómez",
            "dni": "32567890",
            "fechaNacimiento": "12/01/1982",
            "obraSocial": {
                "id": 3457,
                "nombre": "GEA",
                "codigo": "GEA"
            }
        },
        {
            "id": 37099,
            "nombre": "Lorena",
            "apellido": "Guzmán",
            "dni": "33890123",
            "fechaNacimiento": "17/06/1988",
            "obraSocial": {
                "id": 3458,
                "nombre": "GRAFICOS",
                "codigo": "GRAFICOS"
            }
        },
        {
            "id": 37100,
            "nombre": "Ramón",
            "apellido": "Henríquez",
            "dni": "24123456",
            "fechaNacimiento": "03/12/1985",
            "obraSocial": {
                "id": 3459,
                "nombre": "GRIFF S.A.",
                "codigo": "GRIFF"
            }
        },
        {
            "id": 37101,
            "nombre": "Soledad",
            "apellido": "Ibáñez",
            "dni": "25456789",
            "fechaNacimiento": "19/04/1987",
            "obraSocial": {
                "id": 3461,
                "nombre": "IOSFA",
                "codigo": "IOSFA"
            }
        },
        {
            "id": 37102,
            "nombre": "Andrés",
            "apellido": "Jiménez",
            "dni": "26009012",
            "fechaNacimiento": "11/08/1980",
            "obraSocial": {
                "id": 3462,
                "nombre": "JERARQUICOS SALUD",
                "codigo": "JERARQSAL"
            }
        },
        {
            "id": 37103,
            "nombre": "Valentina",
            "apellido": "Lara",
            "dni": "56962531",
            "fechaNacimiento": "06/02/1983",
            "obraSocial": {
                "id": 3463,
                "nombre": "MEDIFE",
                "codigo": "MEDIFE"
            }
        },
        {
            "id": 37104,
            "nombre": "Sergio",
            "apellido": "Lepe",
            "dni": "58966391",
            "fechaNacimiento": "22/10/1981",
            "obraSocial": {
                "id": 3464,
                "nombre": "MET MEDICINA PRIVADA",
                "codigo": "METMEDPR"
            }
        },
        {
            "id": 37105,
            "nombre": "Irene",
            "apellido": "López",
            "dni": "30808449",
            "fechaNacimiento": "08/05/1986",
            "obraSocial": {
                "id": 3465,
                "nombre": "MINISTERIO DE SALUD",
                "codigo": "MINSA"
            }
        },
        {
            "id": 37106,
            "nombre": "Joaquín",
            "apellido": "Maldonado",
            "dni": "32310934",
            "fechaNacimiento": "15/11/1984",
            "obraSocial": {
                "id": 3469,
                "nombre": "NOBIS",
                "codigo": "NOBIS"
            }
        },
        {
            "id": 37107,
            "nombre": "Rocío",
            "apellido": "Mendoza",
            "dni": "51892627",
            "fechaNacimiento": "20/03/1985",
            "obraSocial": {
                "id": 3472,
                "nombre": "OSBLYCA SERVIRED",
                "codigo": "OSBLYCA"
            }
        },
        {
            "id": 37108,
            "nombre": "Pablo",
            "apellido": "Molina",
            "dni": "53594960",
            "fechaNacimiento": "25/07/1979",
            "obraSocial": {
                "id": 3491,
                "nombre": "OSDE",
                "codigo": "OSDE"
            }
        },
        {
            "id": 37109,
            "nombre": "Francisca",
            "apellido": "Morales",
            "dni": "41295574",
            "fechaNacimiento": "19/09/1988",
            "obraSocial": {
                "id": 3463,
                "nombre": "MEDIFE",
                "codigo": "MEDIFE"
            }
        },
        {
            "id": 37110,
            "nombre": "Tomás",
            "apellido": "Nájera",
            "dni": "58926993",
            "fechaNacimiento": "04/01/1982",
            "obraSocial": {
                "id": 3485,
                "nombre": "OMINT",
                "codigo": "OMINT"
            }
        },
        {
            "id": 37111,
            "nombre": "Susana",
            "apellido": "Navarro",
            "dni": "43049984",
            "fechaNacimiento": "13/06/1987",
            "obraSocial": {
                "id": 3514,
                "nombre": "PAMI",
                "codigo": "PAMI"
            }
        },
        {
            "id": 37112,
            "nombre": "Óscar",
            "apellido": "Ochoa",
            "dni": "26999912",
            "fechaNacimiento": "17/12/1980",
            "obraSocial": {
                "id": 3535,
                "nombre": "SWISS MEDICAL",
                "codigo": "SWISSMED"
            }
        },
        {
            "id": 37113,
            "nombre": "Natalia",
            "apellido": "Ortiz",
            "dni": "42110045",
            "fechaNacimiento": "11/04/1985",
            "obraSocial": {
                "id": 3536,
                "nombre": "UNIMED",
                "codigo": "UNIMED"
            }
        },
        {
            "id": 37114,
            "nombre": "Edmundo",
            "apellido": "Parra",
            "dni": "51447740",
            "fechaNacimiento": "23/08/1983",
            "obraSocial": {
                "id": 3537,
                "nombre": "UNION PERSONAL",
                "codigo": "UNIONPERS"
            }
        },
        {
            "id": 37115,
            "nombre": "Antonia",
            "apellido": "Peña",
            "dni": "34797587",
            "fechaNacimiento": "02/10/1981",
            "obraSocial": {
                "id": 3539,
                "nombre": "VISITAR",
                "codigo": "VISITAR"
            }
        },
        {
            "id": 37116,
            "nombre": "Gustavo",
            "apellido": "Pérez",
            "dni": "54728744",
            "fechaNacimiento": "19/05/1986",
            "obraSocial": {
                "id": 3522,
                "nombre": "PRO SALUD",
                "codigo": "PROSALUD"
            }
        }
    ],
    "message": "Pacientes recuperados correctamente",
    "status": 200
}
"""