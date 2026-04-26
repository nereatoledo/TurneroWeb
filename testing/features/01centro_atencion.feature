# language: es
Característica: Crear Centro de Atención

Antecedentes:
  Dado que existe un sistema de gestión de centros de atención

Esquema del escenario: Crear un centro de atención exitosamente
  Cuando el administrador ingresa los datos del centro de atención: "<Nombre>", "<Dirección>", "<Localidad>", "<Provincia>", "<Teléfono>" y "<Coordenadas>"
  Entonces el sistema responde con <status_code> y <status_text>

Ejemplos: Centros de atención exitosos
  | Nombre                      | Dirección                                     | Localidad      | Provincia | Teléfono    | Coordenadas      | status_code | status_text                   |
  | Centro Médico Integral      | Calle 9 de Julio 123, Piso 2, Oficina A       | Puerto Madryn  | Chubut    | 2804451234  | -42.765, -65.034 | 200         | "Centro de atención creado"   |
  | Centro de Salud Rawson      | Avenida Libertad 456                          | Rawson         | Chubut    | 2804482345  | -43.305, -65.102 | 200         | "Centro de atención creado"   |
  | Trelew Salud                | Rivadavia 789, Barrio Centro                  | Trelew         | Chubut    | 2804423456  | -43.252, -65.308 | 200         | "Centro de atención creado"   |
  | Centro Médico Esperanza     | Belgrano 753                                  | Trelew         | Chubut    | 2804429876  | -43.272, -65.311 | 200         | "Centro de atención creado"   |
  | Clinica Rawson              | Mariano Moreno 525                            | Rawson         | Chubut    | 2804481122  | -43.310, -65.112 | 200         | "Centro de atención creado"   |
  | Centro de Rehabilitación    | Hipólito Yrigoyen 852                         | Puerto Madryn  | Chubut    | 2804459988  | -42.755, -65.044 | 200         | "Centro de atención creado"   |
  | Instituto Médico Patagonia  | San Martín 1025, 1er Piso                     | Trelew         | Chubut    | 2804434455  | -43.248, -65.301 | 200         | "Centro de atención creado"   |
  | Centro Odontológico Rawson  | Gobernador Gallina 789                        | Rawson         | Chubut    | 2804487766  | -43.322, -65.123 | 200         | "Centro de atención creado"   |
  | Centro Médico del Este      | Avenida Fontana 987                           | Puerto Madryn  | Chubut    | 2804456677  | -42.777, -65.011 | 200         | "Centro de atención creado"   |

Esquema del escenario: Crear un centro de atención con conflictos
  Cuando el administrador ingresa los datos del centro de atención: "<Nombre>", "<Dirección>", "<Localidad>", "<Provincia>", "<Teléfono>" y "<Coordenadas>"
  Entonces el sistema responde con <status_code> y <status_text>

Ejemplos: Centros de atención con conflictos
  | Nombre                 | Dirección                                     | Localidad     | Provincia | Teléfono   | Coordenadas      | status_code | status_text                                                  |
  | Centro Médico Integral | Calle 9 de Julio 123, Piso 2, Oficina A       | Puerto Madryn | Chubut    | 2804451234 | -42.765, -65.034 | 409         | "Ya existe un centro de atención con ese nombre y dirección" |
  | Centro Médico Nuevo    | Calle 9 de Julio 123, Piso 2, Oficina A       | Puerto Madryn | Chubut    | 2804450000 | -42.795, -65.054 | 409         | "Ya existe un centro de atención con esa dirección"          |
  |                        | Calle 9 de Julio 123, Piso 2, Oficina A       | Puerto Madryn | Chubut    | 2804451111 | -42.765, -65.034 | 400         | "El nombre es requerido"                                     |
  | Centro Médico          |                                               | Puerto Madryn | Chubut    | 2804452222 | -42.765, -65.034 | 400         | "La dirección es requerida"                                  |
  | Centro Sin Contacto    | San Martín 100                                | Trelew        | Chubut    |            | -43.248, -65.301 | 400         | "El teléfono es requerido"                                   |
  | Centro de la Costa     | Belgrano 753                                  | Trelew        | Chubut    | 2804433333 | abc, xyz         | 400         | "Las coordenadas son inválidas"                              |   