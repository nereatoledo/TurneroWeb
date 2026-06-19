            # language: es
            Característica: Listar Pacientes

            Escenario: Recuperar todas los pacientes registrados en el sistema
            Dado que existen 51 pacientes registrados en el sistema
            Cuando un usuario del sistema solicita la lista de pacientes
            Entonces el sistema responde con un JSON de los pacientes:
            """
            {
                "status_code": 200,
                "status_text": "Consulta exitosa",
                "data": [
                    {
                        "nombre": "Alberto",
                        "apellido": "Pasos",
                        "dni": "31590782",
                        "fechaNacimiento": "30/03/1980",
                        "obraSocial": {
                            "nombre": "SANARTE",
                            "codigo": "SANARTE"
                        }
                    },
                    {
                        "nombre": "María",
                        "apellido": "García",
                        "dni": "28456123",
                        "fechaNacimiento": "15/07/1985",
                        "obraSocial": {
                            "nombre": "A.P.M.",
                            "codigo": "APM"
                        }
                    },
                    {
                        "nombre": "Juan",
                        "apellido": "López",
                        "dni": "35789456",
                        "fechaNacimiento": "22/11/1978",
                        "obraSocial": {
                            "nombre": "AMMA OSSACRA",
                            "codigo": "AMMAOSS"
                        }
                    },
                    {
                        "nombre": "Carlos",
                        "apellido": "Rodríguez",
                        "dni": "29123456",
                        "fechaNacimiento": "08/05/1992",
                        "obraSocial": {
                            "nombre": "AMPARA SALUD",
                            "codigo": "AMPARASAL"
                        }
                    },
                    {
                        "nombre": "Ana",
                        "apellido": "Martínez",
                        "dni": "32654321",
                        "fechaNacimiento": "14/02/1988",
                        "obraSocial": {
                            "nombre": "AMTAC",
                            "codigo": "AMTAC"
                        }
                    },
                    {
                        "nombre": "Pedro",
                        "apellido": "González",
                        "dni": "30987654",
                        "fechaNacimiento": "03/09/1975",
                        "obraSocial": {
                            "nombre": "ANDES SALUD",
                            "codigo": "ANDESSAL"
                        }
                    },
                    {
                        "nombre": "Patricia",
                        "apellido": "Fernández",
                        "dni": "27345678",
                        "fechaNacimiento": "19/12/1990",
                        "obraSocial": {
                            "nombre": "APROSS",
                            "codigo": "APROSS"
                        }
                    },
                    {
                        "nombre": "Miguel",
                        "apellido": "Pérez",
                        "dni": "33456789",
                        "fechaNacimiento": "25/06/1982",
                        "obraSocial": {
                            "nombre": "AVALIAN-ACA SALUD",
                            "codigo": "AVALIANACA"
                        }
                    },
                    {
                        "nombre": "Laura",
                        "apellido": "Sánchez",
                        "dni": "31234567",
                        "fechaNacimiento": "11/04/1987",
                        "obraSocial": {
                            "nombre": "BANCARIOS",
                            "codigo": "BANCAR"
                        }
                    },
                    {
                        "nombre": "Roberto",
                        "apellido": "Torres",
                        "dni": "28567890",
                        "fechaNacimiento": "07/10/1979",
                        "obraSocial": {
                            "nombre": "BIENESTAR SALUD S.A.",
                            "codigo": "BIENESTAR"
                        }
                    },
                    {
                        "nombre": "Sandra",
                        "apellido": "Ruiz",
                        "dni": "34678901",
                        "fechaNacimiento": "20/01/1993",
                        "obraSocial": {
                            "nombre": "OSDE",
                            "codigo": "OSDE"
                        }
                    },
                    {
                        "nombre": "Diego",
                        "apellido": "Díaz",
                        "dni": "26789012",
                        "fechaNacimiento": "16/08/1981",
                        "obraSocial": {
                            "nombre": "MEDIFE",
                            "codigo": "MEDIFE"
                        }
                    },
                    {
                        "nombre": "Claudia",
                        "apellido": "Morales",
                        "dni": "29890123",
                        "fechaNacimiento": "09/03/1989",
                        "obraSocial": {
                            "nombre": "SWISS MEDICAL",
                            "codigo": "SWISSMED"
                        }
                    },
                    {
                        "nombre": "Fernando",
                        "apellido": "Castro",
                        "dni": "32901234",
                        "fechaNacimiento": "27/07/1984",
                        "obraSocial": {
                            "nombre": "PAMI",
                            "codigo": "PAMI"
                        }
                    },
                    {
                        "nombre": "Gabriela",
                        "apellido": "Vargas",
                        "dni": "25012345",
                        "fechaNacimiento": "05/11/1986",
                        "obraSocial": {
                            "nombre": "UNION PERSONAL",
                            "codigo": "UNIONPERS"
                        }
                    },
                    {
                        "nombre": "Marco",
                        "apellido": "Álvarez",
                        "dni": "25456123",
                        "fechaNacimiento": "12/05/1980",
                        "obraSocial": {
                            "nombre": "A.P.M.",
                            "codigo": "APM"
                        }
                    },
                    {
                        "nombre": "Verónica",
                        "apellido": "Castro",
                        "dni": "25197012",
                        "fechaNacimiento": "08/11/1983",
                        "obraSocial": {
                            "nombre": "AMMA OSSACRA",
                            "codigo": "AMMAOSS"
                        }
                    },
                    {
                        "nombre": "Silvana",
                        "apellido": "Moreno",
                        "dni": "27123456",
                        "fechaNacimiento": "19/07/1981",
                        "obraSocial": {
                            "nombre": "AMPARA SALUD",
                            "codigo": "AMPARASAL"
                        }
                    },
                    {
                        "nombre": "Ángel",
                        "apellido": "Rojas",
                        "dni": "28456789",
                        "fechaNacimiento": "14/03/1979",
                        "obraSocial": {
                            "nombre": "AMTAC",
                            "codigo": "AMTAC"
                        }
                    },
                    {
                        "nombre": "Gabriela",
                        "apellido": "Solís",
                        "dni": "29789012",
                        "fechaNacimiento": "25/09/1984",
                        "obraSocial": {
                            "nombre": "AMUR ASSIMRA",
                            "codigo": "AMURASS"
                        }
                    },
                    {
                        "nombre": "Rodolfo",
                        "apellido": "Tello",
                        "dni": "30012345",
                        "fechaNacimiento": "02/01/1975",
                        "obraSocial": {
                            "nombre": "ANDES SALUD",
                            "codigo": "ANDESSAL"
                        }
                    },
                    {
                        "nombre": "Adriana",
                        "apellido": "Uribe",
                        "dni": "31345678",
                        "fechaNacimiento": "16/06/1986",
                        "obraSocial": {
                            "nombre": "APROSS",
                            "codigo": "APROSS"
                        }
                    },
                    {
                        "nombre": "Héctor",
                        "apellido": "Valdez",
                        "dni": "32678901",
                        "fechaNacimiento": "30/12/1982",
                        "obraSocial": {
                            "nombre": "AVALIAN-ACA SALUD",
                            "codigo": "AVALIANACA"
                        }
                    },
                    {
                        "nombre": "Marcela",
                        "apellido": "Yáñez",
                        "dni": "33901234",
                        "fechaNacimiento": "11/04/1988",
                        "obraSocial": {
                            "nombre": "BANCARIOS",
                            "codigo": "BANCAR"
                        }
                    },
                    {
                        "nombre": "Javier",
                        "apellido": "Zambrano",
                        "dni": "24234567",
                        "fechaNacimiento": "23/08/1985",
                        "obraSocial": {
                            "nombre": "BIENESTAR SALUD S.A.",
                            "codigo": "BIENESTAR"
                        }
                    },
                    {
                        "nombre": "Beatriz",
                        "apellido": "Acosta",
                        "dni": "25567890",
                        "fechaNacimiento": "07/02/1987",
                        "obraSocial": {
                            "nombre": "BOREAL",
                            "codigo": "BOREAL"
                        }
                    },
                    {
                        "nombre": "Ernesto",
                        "apellido": "Bonilla",
                        "dni": "26890123",
                        "fechaNacimiento": "18/10/1980",
                        "obraSocial": {
                            "nombre": "C.P.C.E.",
                            "codigo": "CPCE"
                        }
                    },
                    {
                        "nombre": "Gloria",
                        "apellido": "Cabrera",
                        "dni": "27012345",
                        "fechaNacimiento": "05/05/1983",
                        "obraSocial": {
                            "nombre": "CAJA NOTARIAL",
                            "codigo": "CAJANOT"
                        }
                    },
                    {
                        "nombre": "Raúl",
                        "apellido": "Delgado",
                        "dni": "28345678",
                        "fechaNacimiento": "21/11/1981",
                        "obraSocial": {
                            "nombre": "CAMI SALUD",
                            "codigo": "CAMISAL"
                        }
                    },
                    {
                        "nombre": "Cecilia",
                        "apellido": "Espinoza",
                        "dni": "29678901",
                        "fechaNacimiento": "09/07/1986",
                        "obraSocial": {
                            "nombre": "DASPU",
                            "codigo": "DASPU"
                        }
                    },
                    {
                        "nombre": "Vicente",
                        "apellido": "Flores",
                        "dni": "30901234",
                        "fechaNacimiento": "14/03/1984",
                        "obraSocial": {
                            "nombre": "GALENO",
                            "codigo": "GALENO"
                        }
                    },
                    {
                        "nombre": "Marisa",
                        "apellido": "García",
                        "dni": "47984684",
                        "fechaNacimiento": "28/09/1979",
                        "obraSocial": {
                            "nombre": "GASTRONOMICOS",
                            "codigo": "GASTRON"
                        }
                    },
                    {
                        "nombre": "Augusto",
                        "apellido": "Gómez",
                        "dni": "32567890",
                        "fechaNacimiento": "12/01/1982",
                        "obraSocial": {
                            "nombre": "GEA",
                            "codigo": "GEA"
                        }
                    },
                    {
                        "nombre": "Lorena",
                        "apellido": "Guzmán",
                        "dni": "33890123",
                        "fechaNacimiento": "17/06/1988",
                        "obraSocial": {
                            "nombre": "GRAFICOS",
                            "codigo": "GRAFICOS"
                        }
                    },
                    {
                        "nombre": "Ramón",
                        "apellido": "Henríquez",
                        "dni": "24123456",
                        "fechaNacimiento": "03/12/1985",
                        "obraSocial": {
                            "nombre": "GRIFF S.A.",
                            "codigo": "GRIFF"
                        }
                    },
                    {
                        "nombre": "Soledad",
                        "apellido": "Ibáñez",
                        "dni": "25456789",
                        "fechaNacimiento": "19/04/1987",
                        "obraSocial": {
                            "nombre": "IOSFA",
                            "codigo": "IOSFA"
                        }
                    },
                    {
                        "nombre": "Andrés",
                        "apellido": "Jiménez",
                        "dni": "26009012",
                        "fechaNacimiento": "11/08/1980",
                        "obraSocial": {
                            "nombre": "JERARQUICOS SALUD",
                            "codigo": "JERARQSAL"
                        }
                    },
                    {
                        "nombre": "Valentina",
                        "apellido": "Lara",
                        "dni": "56962531",
                        "fechaNacimiento": "06/02/1983",
                        "obraSocial": {
                            "nombre": "MEDIFE",
                            "codigo": "MEDIFE"
                        }
                    },
                    {
                        "nombre": "Sergio",
                        "apellido": "Lepe",
                        "dni": "58966391",
                        "fechaNacimiento": "22/10/1981",
                        "obraSocial": {
                            "nombre": "MET MEDICINA PRIVADA",
                            "codigo": "METMEDPR"
                        }
                    },
                    {
                        "nombre": "Irene",
                        "apellido": "López",
                        "dni": "30808449",
                        "fechaNacimiento": "08/05/1986",
                        "obraSocial": {
                            "nombre": "MINISTERIO DE SALUD",
                            "codigo": "MINSA"
                        }
                    },
                    {
                        "nombre": "Joaquín",
                        "apellido": "Maldonado",
                        "dni": "32310934",
                        "fechaNacimiento": "15/11/1984",
                        "obraSocial": {
                            "nombre": "NOBIS",
                            "codigo": "NOBIS"
                        }
                    },
                    {
                        "nombre": "Rocío",
                        "apellido": "Mendoza",
                        "dni": "51892627",
                        "fechaNacimiento": "20/03/1985",
                        "obraSocial": {
                            "nombre": "OSBLYCA SERVIRED",
                            "codigo": "OSBLYCA"
                        }
                    },
                    {
                        "nombre": "Pablo",
                        "apellido": "Molina",
                        "dni": "53594960",
                        "fechaNacimiento": "25/07/1979",
                        "obraSocial": {
                            "nombre": "OSDE",
                            "codigo": "OSDE"
                        }
                    },
                    {
                        "nombre": "Francisca",
                        "apellido": "Morales",
                        "dni": "41295574",
                        "fechaNacimiento": "19/09/1988",
                        "obraSocial": {
                            "nombre": "MEDIFE",
                            "codigo": "MEDIFE"
                        }
                    },
                    {
                        "nombre": "Tomás",
                        "apellido": "Nájera",
                        "dni": "58926993",
                        "fechaNacimiento": "04/01/1982",
                        "obraSocial": {
                            "nombre": "OMINT",
                            "codigo": "OMINT"
                        }
                    },
                    {
                        "nombre": "Susana",
                        "apellido": "Navarro",
                        "dni": "43049984",
                        "fechaNacimiento": "13/06/1987",
                        "obraSocial": {
                            "nombre": "PAMI",
                            "codigo": "PAMI"
                        }
                    },
                    {
                        "nombre": "Óscar",
                        "apellido": "Ochoa",
                        "dni": "26999912",
                        "fechaNacimiento": "17/12/1980",
                        "obraSocial": {
                            "nombre": "SWISS MEDICAL",
                            "codigo": "SWISSMED"
                        }
                    },
                    {
                        "nombre": "Natalia",
                        "apellido": "Ortiz",
                        "dni": "42110045",
                        "fechaNacimiento": "11/04/1985",
                        "obraSocial": {
                            "nombre": "UNIMED",
                            "codigo": "UNIMED"
                        }
                    },
                    {
                        "nombre": "Edmundo",
                        "apellido": "Parra",
                        "dni": "51447740",
                        "fechaNacimiento": "23/08/1983",
                        "obraSocial": {
                            "nombre": "UNION PERSONAL",
                            "codigo": "UNIONPERS"
                        }
                    },
                    {
                        "nombre": "Antonia",
                        "apellido": "Peña",
                        "dni": "34797587",
                        "fechaNacimiento": "02/10/1981",
                        "obraSocial": {
                            "nombre": "VISITAR",
                            "codigo": "VISITAR"
                        }
                    },
                    {
                        "nombre": "Gustavo",
                        "apellido": "Pérez",
                        "dni": "54728744",
                        "fechaNacimiento": "19/05/1986",
                        "obraSocial": {
                            "nombre": "PRO SALUD",
                            "codigo": "PROSALUD"
                        }
                    }
                ],
                "message": "Pacientes recuperados correctamente",
                "status": 200
            }
            """