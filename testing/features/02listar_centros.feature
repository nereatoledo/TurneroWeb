            #Modifique las coordenadas porque en el backend me devuelve un Objeto JSON. Tmabien cambie el "status": 200, "message": "OK","data": para que coincidan con el response.java.
            # language: es

            Característica: Listar Centros de Atención

            Antecedentes:
            Dado que existen centros de atención creados en el sistema
            Y los siguientes centros de atención han sido registrados:
            | Nombre                        | Dirección                               | Localidad     | Provincia | Coordenadas      | Teléfono |
            | Clinica Veterinaria del Golfo | Almirante Brown 456                     | Puerto Madryn | Chubut    | -42.789, -65.021 |2804898989|

            Escenario: Listar todos los centros de atención
            Cuando el usuario solicita la lista de centros de atención
            Entonces el sistema responde con status_code 200 y status_text "OK"
            Y el cuerpo de la respuesta contiene un array JSON con la siguiente estructura:
            """
            {
                "status": 200,
                "message": "OK",
                "data": [
                    {
                        "nombre": "Centro Médico Integral",
                        "direccion": "Calle 9 de Julio 123, Piso 2, Oficina A",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -42.765,
                            "longitud": -65.034
                        }
                    },
                    {
                        "nombre": "Centro de Salud Rawson",
                        "direccion": "Avenida Libertad 456",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.305,
                            "longitud": -65.102
                        }
                    },
                    {
                        "nombre": "Trelew Salud",
                        "direccion": "Rivadavia 789, Barrio Centro",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.252,
                            "longitud": -65.308
                        }
                    },
                    {
                        "nombre": "Clinica Veterinaria del Golfo",
                        "direccion": "Almirante Brown 456",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -42.789,
                            "longitud": -65.021
                        }
                    },
                    {
                        "nombre": "Centro Médico Esperanza",
                        "direccion": "Belgrano 753",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.272,
                            "longitud": -65.311
                        }
                    },
                    {
                        "nombre": "Clinica Rawson",
                        "direccion": "Mariano Moreno 525",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.31,
                            "longitud": -65.112
                        }
                    },
                    {
                        "nombre": "Centro de Rehabilitación",
                        "direccion": "Hipólito Yrigoyen 852",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -42.755,
                            "longitud": -65.044
                        }
                    },
                    {
                        "nombre": "Instituto Médico Patagonia",
                        "direccion": "San Martín 1025, 1er Piso",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.248,
                            "longitud": -65.301
                        }
                    },
                    {
                        "nombre": "Centro Odontológico Rawson",
                        "direccion": "Gobernador Gallina 789",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -43.322,
                            "longitud": -65.123
                        }
                    },
                    {
                        "nombre": "Centro Médico del Este",
                        "direccion": "Avenida Fontana 987",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": {
                            "latitud": -42.777,
                            "longitud": -65.011
                        }
                    }
                ]
            }
            """