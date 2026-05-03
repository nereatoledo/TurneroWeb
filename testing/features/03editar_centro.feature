# language: es
Característica: Modificar Centro de Atención


  Esquema del escenario: Intentar modificar centro de atención con conflictos
    Cuando el administrador modifica los datos del centro de atención "<NombreOriginal>" con los siguientes atributos:
      | Nombre        | Dirección        | Localidad        | Provincia        | Coordenadas        |
      | <NombreNuevo> | <DireccionNueva> | <LocalidadNueva> | <ProvinciaNueva> | <CoordenadasNueva> |
    Entonces el sistema responde con <status_code> y "<status_text>"

    Ejemplos: Modificaciones con conflictos
      | NombreOriginal         | NombreNuevo            | DireccionNueva               | LocalidadNueva | ProvinciaNueva | CoordenadasNueva | status_code | status_text                                                |
      | Centro Médico Integral | Centro Médico Integral | Avenida Libertad 456         | Puerto Madryn  | Chubut         | -42.765, -65.034 | 409         | Ya existe un centro de atención con esa dirección          |
      | Centro de Salud Rawson | Centro de Salud Rawson | Avenida Libertad 456         | Rawson         | Chubut         | -43.305, -65.102 | 409         | Ya existe un centro de atención con ese nombre y dirección |
      | Trelew Salud           | Trelew Salud           | Rivadavia 789, Barrio Centro | Trelew         | Chubut         | abc, xyz         | 400         | Las coordenadas son inválidas                              |


  Esquema del escenario: Modificar centro de atención exitosamente
    Cuando el administrador modifica los datos del centro de atención "<NombreOriginal>" con los siguientes atributos:
      | Nombre        | Dirección        | Localidad        | Provincia        | Coordenadas        |
      | <NombreNuevo> | <DireccionNueva> | <LocalidadNueva> | <ProvinciaNueva> | <CoordenadasNueva> |
    Entonces el sistema responde con <status_code> y "<status_text>"

    Ejemplos: Modificaciones exitosas
      | NombreOriginal         | NombreNuevo                    | DireccionNueva                          | LocalidadNueva | ProvinciaNueva | CoordenadasNueva | status_code | status_text                   |
      | Centro Médico Integral | Centro Médico Integral         | Calle 9 de Julio 150, Piso 3, Oficina B | Puerto Madryn  | Chubut         | -42.765, -65.034 | 200         | Centro de atención modificado |
      | Centro de Salud Rawson | Centro de Salud Dr. Juan Perez | Avenida Libertad 456                    | Rawson         | Chubut         | -43.305, -65.102 | 200         | Centro de atención modificado | 
      | Trelew Salud           | Trelew Salud                   | Rivadavia 789, Barrio Centro            | Trelew         | Chubut         | -43.255, -65.310 | 200         | Centro de atención modificado | s