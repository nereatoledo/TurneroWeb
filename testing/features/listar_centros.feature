            # language: es

            Característica: Listar Centros de Atención

            Antecedentes:
            Dado que existen centros de atención creados en el sistema
            Y los siguientes centros de atención han sido registrados:
            | Nombre                        | Dirección                               | Localidad     | Provincia | Coordenadas      |
            | Centro Médico Integral        | Calle 9 de Julio 123, Piso 2, Oficina A | Puerto Madryn | Chubut    | -42.765, -65.034 |
            | Centro de Salud Rawson        | Avenida Libertad 456                    | Rawson        | Chubut    | -43.305, -65.102 |
            | Trelew Salud                  | Rivadavia 789, Barrio Centro            | Trelew        | Chubut    | -43.252, -65.308 |
            | Clinica Veterinaria del Golfo | Almirante Brown 456                     | Puerto Madryn | Chubut    | -42.789, -65.021 |
            | Centro Médico Esperanza       | Belgrano 753                            | Trelew        | Chubut    | -43.272, -65.311 |
            | Clinica Rawson                | Mariano Moreno 525                      | Rawson        | Chubut    | -43.310, -65.112 |
            | Centro de Rehabilitación      | Hipólito Yrigoyen 852                   | Puerto Madryn | Chubut    | -42.755, -65.044 |
            | Instituto Médico Patagonia    | San Martín 1025, 1er Piso               | Trelew        | Chubut    | -43.248, -65.301 |
            | Centro Odontológico Rawson    | Gobernador Gallina 789                  | Rawson        | Chubut    | -43.322, -65.123 |
            | Centro Médico del Este        | Avenida Fontana 987                     | Puerto Madryn | Chubut    | -42.777, -65.011 |

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
                        "coordenadas": "-42.765, -65.034"
                    },
                    {
                        "nombre": "Centro de Salud Rawson",
                        "direccion": "Avenida Libertad 456",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": "-43.305, -65.102"
                    },
                    {
                        "nombre": "Trelew Salud",
                        "direccion": "Rivadavia 789, Barrio Centro",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": "-43.252, -65.308"
                    },
                    {
                        "nombre": "Centro Médico Esperanza",
                        "direccion": "Belgrano 753",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": "-43.272, -65.311"
                    },
                    {
                        "nombre": "Clinica Rawson",
                        "direccion": "Mariano Moreno 525",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": "-43.310, -65.112"
                    },
                    {
                        "nombre": "Centro de Rehabilitación",
                        "direccion": "Hipólito Yrigoyen 852",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": "-42.755, -65.044"
                    },
                    {
                        "nombre": "Instituto Médico Patagonia",
                        "direccion": "San Martín 1025, 1er Piso",
                        "localidad": "Trelew",
                        "provincia": "Chubut",
                        "coordenadas": "-43.248, -65.301"
                    },
                    {
                        "nombre": "Centro Odontológico Rawson",
                        "direccion": "Gobernador Gallina 789",
                        "localidad": "Rawson",
                        "provincia": "Chubut",
                        "coordenadas": "-43.322, -65.123"
                    },
                    {
                        "nombre": "Centro Médico del Este",
                        "direccion": "Avenida Fontana 987",
                        "localidad": "Puerto Madryn",
                        "provincia": "Chubut",
                        "coordenadas": "-42.777, -65.011"
                    }
                ]
            }
            """