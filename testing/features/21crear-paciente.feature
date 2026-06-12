# language: es

Característica: Crear paciente

Esquema del escenario: Crear un paciente exitosamente
Dado que existe la obra social "<obraSocial>"
Cuando el administrador crea un paciente con "<nombre>", "<apellido>", "<dni>", "<fechaNacimiento>" y "<obraSocial>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| nombre    | apellido  | dni      | fechaNacimiento | obraSocial           | status_code | status_text                      |
| Alberto   | Pasos     | 31590782 | 30/03/1980      | SANARTE              | 200         | Paciente ingresado correctamente |
| María     | García    | 28456123 | 15/07/1985      | A.P.M.               | 200         | Paciente ingresado correctamente |
| Juan      | López     | 35789456 | 22/11/1978      | AMMA OSSACRA         | 200         | Paciente ingresado correctamente |
| Carlos    | Rodríguez | 29123456 | 08/05/1992      | AMPARA SALUD         | 200         | Paciente ingresado correctamente |
| Ana       | Martínez  | 32654321 | 14/02/1988      | AMTAC                | 200         | Paciente ingresado correctamente |
| Pedro     | González  | 30987654 | 03/09/1975      | ANDES SALUD          | 200         | Paciente ingresado correctamente |
| Patricia  | Fernández | 27345678 | 19/12/1990      | APROSS               | 200         | Paciente ingresado correctamente |
| Miguel    | Pérez     | 33456789 | 25/06/1982      | AVALIAN-ACA SALUD    | 200         | Paciente ingresado correctamente |
| Laura     | Sánchez   | 31234567 | 11/04/1987      | BANCARIOS            | 200         | Paciente ingresado correctamente |
| Roberto   | Torres    | 28567890 | 07/10/1979      | BIENESTAR SALUD S.A. | 200         | Paciente ingresado correctamente |
| Sandra    | Ruiz      | 34678901 | 20/01/1993      | OSDE                 | 200         | Paciente ingresado correctamente |
| Diego     | Díaz      | 26789012 | 16/08/1981      | MEDIFE               | 200         | Paciente ingresado correctamente |
| Claudia   | Morales   | 29890123 | 09/03/1989      | SWISS MEDICAL        | 200         | Paciente ingresado correctamente |
| Fernando  | Castro    | 32901234 | 27/07/1984      | PAMI                 | 200         | Paciente ingresado correctamente |
| Gabriela  | Vargas    | 25012345 | 05/11/1986      | UNION PERSONAL       | 200         | Paciente ingresado correctamente |
| Marco     | Álvarez   | 25456123 | 12/05/1980      | A.P.M.               | 200         | Paciente ingresado correctamente |
| Verónica  | Castro    | 25197012 | 08/11/1983      | AMMA OSSACRA         | 200         | Paciente ingresado correctamente |
| Silvana   | Moreno    | 27123456 | 19/07/1981      | AMPARA SALUD         | 200         | Paciente ingresado correctamente |
| Ángel     | Rojas     | 28456789 | 14/03/1979      | AMTAC                | 200         | Paciente ingresado correctamente |
| Gabriela  | Solís     | 29789012 | 25/09/1984      | AMUR ASSIMRA         | 200         | Paciente ingresado correctamente |
| Rodolfo   | Tello     | 30012345 | 02/01/1975      | ANDES SALUD          | 200         | Paciente ingresado correctamente |
| Adriana   | Uribe     | 31345678 | 16/06/1986      | APROSS               | 200         | Paciente ingresado correctamente |
| Héctor    | Valdez    | 32678901 | 30/12/1982      | AVALIAN-ACA SALUD    | 200         | Paciente ingresado correctamente |
| Marcela   | Yáñez     | 33901234 | 11/04/1988      | BANCARIOS            | 200         | Paciente ingresado correctamente |
| Javier    | Zambrano  | 24234567 | 23/08/1985      | BIENESTAR SALUD S.A. | 200         | Paciente ingresado correctamente |
| Beatriz   | Acosta    | 25567890 | 07/02/1987      | BOREAL               | 200         | Paciente ingresado correctamente |
| Ernesto   | Bonilla   | 26890123 | 18/10/1980      | C.P.C.E.             | 200         | Paciente ingresado correctamente |
| Gloria    | Cabrera   | 27012345 | 05/05/1983      | CAJA NOTARIAL        | 200         | Paciente ingresado correctamente |
| Raúl      | Delgado   | 28345678 | 21/11/1981      | CAMI SALUD           | 200         | Paciente ingresado correctamente |
| Cecilia   | Espinoza  | 29678901 | 09/07/1986      | DASPU                | 200         | Paciente ingresado correctamente |
| Vicente   | Flores    | 30901234 | 14/03/1984      | GALENO               | 200         | Paciente ingresado correctamente |
| Marisa    | García    | 47984684 | 28/09/1979      | GASTRONOMICOS        | 200         | Paciente ingresado correctamente |
| Augusto   | Gómez     | 32567890 | 12/01/1982      | GEA                  | 200         | Paciente ingresado correctamente |
| Lorena    | Guzmán    | 33890123 | 17/06/1988      | GRAFICOS             | 200         | Paciente ingresado correctamente |
| Ramón     | Henríquez | 24123456 | 03/12/1985      | GRIFF S.A.           | 200         | Paciente ingresado correctamente |
| Soledad   | Ibáñez    | 25456789 | 19/04/1987      | IOSFA                | 200         | Paciente ingresado correctamente |
| Andrés    | Jiménez   | 26009012 | 11/08/1980      | JERARQUICOS SALUD    | 200         | Paciente ingresado correctamente |
| Valentina | Lara      | 56962531 | 06/02/1983      | MEDIFE               | 200         | Paciente ingresado correctamente |
| Sergio    | Lepe      | 58966391 | 22/10/1981      | MET MEDICINA PRIVADA | 200         | Paciente ingresado correctamente |
| Irene     | López     | 30808449 | 08/05/1986      | MINISTERIO DE SALUD  | 200         | Paciente ingresado correctamente |
| Joaquín   | Maldonado | 32310934 | 15/11/1984      | NOBIS                | 200         | Paciente ingresado correctamente |
| Rocío     | Mendoza   | 51892627 | 20/03/1985      | OSBLYCA SERVIRED     | 200         | Paciente ingresado correctamente |
| Pablo     | Molina    | 53594960 | 25/07/1979      | OSDE                 | 200         | Paciente ingresado correctamente |
| Francisca | Morales   | 41295574 | 19/09/1988      | MEDIFE               | 200         | Paciente ingresado correctamente |
| Tomás     | Nájera    | 58926993 | 04/01/1982      | OMINT                | 200         | Paciente ingresado correctamente |
| Susana    | Navarro   | 43049984 | 13/06/1987      | PAMI                 | 200         | Paciente ingresado correctamente |
| Óscar     | Ochoa     | 26999912 | 17/12/1980      | SWISS MEDICAL        | 200         | Paciente ingresado correctamente |
| Natalia   | Ortiz     | 42110045 | 11/04/1985      | UNIMED               | 200         | Paciente ingresado correctamente |
| Edmundo   | Parra     | 51447740 | 23/08/1983      | UNION PERSONAL       | 200         | Paciente ingresado correctamente |
| Antonia   | Peña      | 34797587 | 02/10/1981      | VISITAR              | 200         | Paciente ingresado correctamente |
| Gustavo   | Pérez     | 54728744 | 19/05/1986      | PRO SALUD            | 200         | Paciente ingresado correctamente |


Esquema del escenario: Crear un paciente invalido
Dado que existe la obra social "<obraSocial>"
Cuando el administrador crea un paciente con "<nombre>", "<apellido>", "<dni>", "<fechaNacimiento>" y "<obraSocial>"
Entonces el sistema responde con <status_code> y "<status_text>"

Ejemplos:
| nombre   | apellido | dni        | fechaNacimiento | obraSocial | status_code | status_text                                |
| Diego    | Méndez   | 28456123   | 15/11/1984      | VISITAR    | 409         | El dni ya existe en el sistema             |
| Laura    | Ramírez  | 248348--66 | 13/06/1987      | VISITAR    | 409         | dni incorrecto, débe contener sólo números |
| Carlos   | Díaz     |            | 11/04/1985      | VISITAR    | 409         | El dni es obligatorio                      |
| Alberto  | Armando  | 40771664   |                 | VISITAR    | 409         | La fecha de nacimiento es obligatoria      |
|          | Sánchez  | 8888888    | 02/10/1981      | VISITAR    | 409         | El Nombre es obligatorio                   |
| Patricia |          | 77777777   | 19/05/1986      | VISITAR    | 409         | El apellido es obligatorio                 |